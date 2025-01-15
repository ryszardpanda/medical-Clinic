package com.ryszardpanda.medicalClinic.service;

import com.ryszardpanda.medicalClinic.exceptions.NoIdNumberException;
import com.ryszardpanda.medicalClinic.exceptions.PersonNotFoundException;
import com.ryszardpanda.medicalClinic.mapper.DoctorMapper;
import com.ryszardpanda.medicalClinic.model.ChangePasswordDTO;
import com.ryszardpanda.medicalClinic.model.Doctor;
import com.ryszardpanda.medicalClinic.model.DoctorEditDTO;
import com.ryszardpanda.medicalClinic.model.Institution;
import com.ryszardpanda.medicalClinic.repository.DoctorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DoctorServiceTest {
    private DoctorRepository doctorRepository;
    private DoctorMapper doctorMapper;
    private DoctorService doctorService;

    @BeforeEach
    void setUp(){
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.doctorMapper = Mappers.getMapper(DoctorMapper.class);
        this.doctorService = new DoctorService(doctorRepository, doctorMapper);
    }

    @Test
    public void addDoctor_DoctorDoesNotExist_DoctorReturned(){
        //given
        DoctorEditDTO doctorEditDTO = new DoctorEditDTO(1L,"Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka");
        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());

        when(doctorRepository.findByEmail("marcinn@op.pl")).thenReturn(Optional.empty());
        when(doctorRepository.save(any())).thenReturn(doctor);
        //when
        Doctor result = doctorService.addDoctor(doctorEditDTO);
        //then
        Assertions.assertEquals("marcinn@op.pl", result.getEmail());
        Assertions.assertEquals("Marcin", result.getFirstName());
        Assertions.assertEquals("Macinowski", result.getLastName());
        Assertions.assertEquals("eloelo", result.getPassword());
        Assertions.assertEquals(doctorEditDTO.getSpecialization(), result.getSpecialization());
    }

    @Test
    public void getDoctorByEmail_DoctorDoesExist_doctorFound(){
        //given
        DoctorEditDTO doctorEditDTO = new DoctorEditDTO(1L,"Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka");
        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());

        when(doctorRepository.findByEmail("marcinn@op.pl")).thenReturn(Optional.of(doctor));
        //when
        Doctor result = doctorService.getDoctorByEmail(doctor.getEmail());
        //then
        Assertions.assertEquals(doctorEditDTO.getFirstName(), result.getFirstName());
        Assertions.assertEquals(doctorEditDTO.getLastName(), result.getLastName());
        Assertions.assertEquals(doctorEditDTO.getEmail(), result.getEmail());
        Assertions.assertEquals(doctorEditDTO.getPassword(), result.getPassword());
        Assertions.assertEquals(doctorEditDTO.getSpecialization(), result.getSpecialization());
    }

    @Test
    public void deleteDoctor_DoctorExist_DoctorDeleted(){
        //given
        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());
        when(doctorRepository.findByEmail("marcinn@op.pl")).thenReturn(Optional.of(doctor));
        //when
        doctorService.deleteDoctorByEmail("marcinn@op.pl");
        //then
        verify(doctorRepository, times(1)).delete(doctor);
    }

    @Test
    public void updateDoctor_DoctorDoesExist_DoctorUpdated(){
        //given
        DoctorEditDTO updatedDoctor = new DoctorEditDTO(1L,"Marcineeek", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka");
        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());

        when(doctorRepository.findByEmail("marcinn@op.pl")).thenReturn(Optional.of(doctor));
        //when
        Doctor result = doctorService.updateDoctor("marcinn@op.pl", updatedDoctor);
        //then
        Assertions.assertEquals("marcinn@op.pl", result.getEmail());
        Assertions.assertEquals("Marcineeek", result.getFirstName());
        Assertions.assertEquals("Macinowski", result.getLastName());
        Assertions.assertEquals("eloelo", result.getPassword());
        Assertions.assertEquals(updatedDoctor.getSpecialization(), result.getSpecialization());
    }

    @Test
    public void updateDoctorPassword_DoctorExist_PasswordUpdated(){
        //given
        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("123");
        when(doctorRepository.findByEmail("marcinn@op.pl")).thenReturn(Optional.of(doctor));
        //when
        Doctor result = doctorService.updatePassword("marcinn@op.pl", changePasswordDTO);
        //then
        Assertions.assertEquals("123", result.getPassword());
    }

    @Test
    public void findDoctorById_DoctorExist_doctorFound(){
        //given
        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        //when
        Doctor result = doctorService.findDoctorById(1L);
        Assertions.assertEquals("marcinn@op.pl", result.getEmail());
        Assertions.assertEquals("Marcin", result.getFirstName());
        Assertions.assertEquals("Macinowski", result.getLastName());
        Assertions.assertEquals("eloelo", result.getPassword());
        Assertions.assertEquals("kombinatoryka", result.getSpecialization());
    }

    @Test
    public void getDoctorByEmail_DoctorDoesntExist_PersonNotFoundException(){
        //given
        when(doctorRepository.findByEmail(any())).thenReturn(Optional.empty());
        //when//then
        PersonNotFoundException result = Assertions.assertThrows(PersonNotFoundException.class, () -> doctorService.getDoctorByEmail("eloelo"));
        Assertions.assertEquals("Nie znaleziono Doktora o takim emailu.", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
    }

    @Test
    public void deleteDoctorByEmail_DoctorDoesntExist_PersonNotFoundException(){
        //given
        when(doctorRepository.findByEmail(any())).thenReturn(Optional.empty());
        //when//then
        PersonNotFoundException result = Assertions.assertThrows(PersonNotFoundException.class, () -> doctorService.deleteDoctorByEmail("eloelo"));
        Assertions.assertEquals("Nie znaleziono Doktora o takim emailu.", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
    }

    @Test
    public void updateDoctor_DoctorDoesntExist_PersonNotFoundException(){
        //given
        DoctorEditDTO updatedDoctor = new DoctorEditDTO(1L,"Marcineeek", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka");
        when(doctorRepository.findByEmail(any())).thenReturn(Optional.empty());
        //when
        PersonNotFoundException result = Assertions.assertThrows(PersonNotFoundException.class, () -> doctorService.updateDoctor("eloelo", updatedDoctor));
        //then
        Assertions.assertEquals("Nie znaleziono Doktora o takim emailu.", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
    }

    @Test
    public void updatePassword_DoctorDoesntExist_PersonNotFoundException(){
        //given
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("123");
        when(doctorRepository.findByEmail(any())).thenReturn(Optional.empty());
        //when
        PersonNotFoundException result = Assertions.assertThrows(PersonNotFoundException.class, () -> doctorService.updatePassword("eloelo", changePasswordDTO));
        //then
        Assertions.assertEquals("Nie znaleziono Doktora o takim emailu.", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
    }

    @Test
    public void findDoctorById_DoctorDoesntExist_PersonNotFoundException(){
        //given
        when(doctorRepository.findById(any())).thenReturn(Optional.empty());
        //when//then
        NoIdNumberException result = Assertions.assertThrows(NoIdNumberException.class, () -> doctorService.findDoctorById(2L));
        Assertions.assertEquals("Nie znaleziono Doktora o takim ID", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }


}
