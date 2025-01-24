package com.ryszardpanda.medicalClinic.service;

import com.ryszardpanda.medicalClinic.exceptions.NoIdNumberException;
import com.ryszardpanda.medicalClinic.exceptions.PersonNotFoundException;
import com.ryszardpanda.medicalClinic.mapper.PatientMapper;
import com.ryszardpanda.medicalClinic.model.ChangePasswordDTO;
import com.ryszardpanda.medicalClinic.model.Patient;
import com.ryszardpanda.medicalClinic.model.PatientEditDTO;
import com.ryszardpanda.medicalClinic.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    private PatientRepository patientRepository;
    private PatientMapper patientMapper;
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.patientMapper = Mappers.getMapper(PatientMapper.class);
        this.patientService = new PatientService(patientRepository, patientMapper);
    }

    @Test
    @DisplayName("")
    public void addPatient_PatientsDoesNotExist_patientReturned() {
        //given
        PatientEditDTO patientEditDTO = new PatientEditDTO("Marcin", "Sobolewski",
                "marcinek420@op.pl", "marcinek420", "A1234", "272373734788",
                LocalDate.of(2022, 11, 11));

        Patient patient = new Patient(1L, "Marcin", "Sobolewski", "marcinek420@op.pl", "marcinek420", "A1234", "272373734788",
                LocalDate.of(2022, 11, 11));

        when(patientRepository.findByEmail("marcinek420@op.pl")).thenReturn(Optional.empty());
        when(patientRepository.save(any())).thenReturn(patient);

        //when
        Patient result = patientService.addPatient(patientEditDTO);
        //then
        Assertions.assertEquals("marcinek420@op.pl", result.getEmail());
        Assertions.assertEquals("Marcin", result.getFirstName());
        Assertions.assertEquals("Sobolewski", result.getLastName());
        Assertions.assertEquals("marcinek420", result.getPassword());
        Assertions.assertEquals("A1234", result.getIdCardNo());
        Assertions.assertEquals("272373734788", result.getPhoneNumber());
        Assertions.assertEquals(LocalDate.of(2022, 11, 11), result.getBirthday());
    }

    @Test
    public void updatePatient_PatientDoesExist_patientUpdated() {
        //given
        PatientEditDTO updatedPatient = new PatientEditDTO("Marcinnnnn", "Sobolewski",
                "marcinek420@op.pl", "marcinek420", "A1234", "272373734788",
                LocalDate.of(2022, 11, 11));

        Patient patient = new Patient(1L, "Marcin", "Sobolewski", "marcinek420@op.pl", "marcinek420", "A1234", "272373734788",
                LocalDate.of(2022, 11, 11));

        when(patientRepository.findByEmail("marcinek420@op.pl")).thenReturn(Optional.of(patient));
        //when
        Patient result = patientService.updatePatient("marcinek420@op.pl", updatedPatient);

        //then
        Assertions.assertEquals("marcinek420@op.pl", result.getEmail());
        Assertions.assertEquals("Marcinnnnn", result.getFirstName());
        Assertions.assertEquals("Sobolewski", result.getLastName());
        Assertions.assertEquals("marcinek420", result.getPassword());
        Assertions.assertEquals("A1234", result.getIdCardNo());
        Assertions.assertEquals("272373734788", result.getPhoneNumber());
        Assertions.assertEquals(LocalDate.of(2022, 11, 11), result.getBirthday());

    }

    @Test
    public void updatePassword_PatientDoesExist_passwordUpdated() {
        //given
        Patient patient = new Patient(1L, "Marcin", "Sobolewski", "marcinek420@op.pl", "marcinek420", "A1234", "272373734788",
                LocalDate.of(2022, 11, 11));

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("123");

        when(patientRepository.findByEmail("marcinek420@op.pl")).thenReturn(Optional.of(patient));
        //when
        Patient result = patientService.updatePassword("marcinek420@op.pl", changePasswordDTO);
        //then
        Assertions.assertEquals("123", result.getPassword());
    }

    @Test
    public void deletePatientByEmail_PatientDoesExist_patientDeleted() {
        //given
        Patient patient = new Patient(1L, "Marcin", "Sobolewski", "marcinek420@op.pl", "marcinek420", "A1234", "272373734788",
                LocalDate.of(2022, 11, 11));

        String email = patient.getEmail();
        when(patientRepository.findByEmail("marcinek420@op.pl")).thenReturn(Optional.of(patient));
        doNothing().when(patientRepository).delete(patient);
        //when
        patientService.deletePatientByEmail(email);
        //then
        verify(patientRepository, times(1)).delete(patient);
    }

    @Test
    public void getPatientByEmail_PatientDoesExist_patientFound() {
        //given
        Patient patient = new Patient(1L, "Marcin", "Sobolewski", "marcinek420@op.pl", "marcinek420", "A1234", "272373734788",
                LocalDate.of(2022, 11, 11));

        String email = patient.getEmail();

        when(patientRepository.findByEmail("marcinek420@op.pl")).thenReturn(Optional.of(patient));
        //when
        Patient result = patientService.getPatientByEmail(email);
        //then
        Assertions.assertEquals("marcinek420@op.pl", result.getEmail());
        Assertions.assertEquals("Marcin", result.getFirstName());
        Assertions.assertEquals("Sobolewski", result.getLastName());
        Assertions.assertEquals("marcinek420", result.getPassword());
        Assertions.assertEquals("A1234", result.getIdCardNo());
        Assertions.assertEquals("272373734788", result.getPhoneNumber());
        Assertions.assertEquals(LocalDate.of(2022, 11, 11), result.getBirthday());
    }


    @Test
    public void findPatientById_PatientDoesExist_patientFound() {
        //given
        Patient patient = new Patient(1L, "Marcin", "Sobolewski", "marcinek420@op.pl", "marcinek420", "A1234", "272373734788",
                LocalDate.of(2022, 11, 11));

        Long id = patient.getId();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        // when
        Patient result = patientService.findPatientById(id);

        // then
        Assertions.assertEquals("marcinek420@op.pl", result.getEmail());
        Assertions.assertEquals("Marcin", result.getFirstName());
        Assertions.assertEquals("Sobolewski", result.getLastName());
        Assertions.assertEquals("marcinek420", result.getPassword());
        Assertions.assertEquals("A1234", result.getIdCardNo());
        Assertions.assertEquals("272373734788", result.getPhoneNumber());
        Assertions.assertEquals(LocalDate.of(2022, 11, 11), result.getBirthday());
    }

    @Test
    public void getPatientByEmail_PatietnNotExist_pateientNotFoundException(){
        //given
        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());
        //when/then
        PersonNotFoundException result = Assertions.assertThrows(PersonNotFoundException.class, () -> patientService.getPatientByEmail("eloelo"));
        Assertions.assertEquals("Nie znaleziono Pacjenta o takim emailu.", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
    }

    @Test
    public void updatePatient_PatientDoesNotExist_PersonNotFoundException(){
        //given
        PatientEditDTO updatedPatient = new PatientEditDTO("Marcinnnnn", "Sobolewski",
                "marcinek420@op.pl", "marcinek420", "A1234", "272373734788",
                LocalDate.of(2022, 11, 11));        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());
        //when
        PersonNotFoundException result = Assertions.assertThrows(PersonNotFoundException.class, () -> patientService.updatePatient("eloelo", updatedPatient));
        //then
        Assertions.assertEquals("Nie znaleziono Pacjenta o takim emailu.", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
    }

    @Test
    public void updatePassword_PatientDoesNotExist_PersonNotFoundException(){
        //given
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("123");
        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());
        //when
        PersonNotFoundException result = Assertions.assertThrows(PersonNotFoundException.class, () -> patientService.updatePassword("eloelo", changePasswordDTO));
        //then
        Assertions.assertEquals("Nie znaleziono Pacjenta o takim emailu.", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
    }

    @Test
    public void findPatientById_PatientDoesntExist_PersonNotFoundException(){
        when(patientRepository.findById(any())).thenReturn(Optional.empty());

        NoIdNumberException result = Assertions.assertThrows(NoIdNumberException.class, () -> patientService.findPatientById(2L));

        Assertions.assertEquals("Nie znaleziono Pacjenta o takim ID", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }
}