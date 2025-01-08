package com.ryszardpanda.medicalClinic.service;

import com.ryszardpanda.medicalClinic.exceptions.InstitutionNotFoundException;
import com.ryszardpanda.medicalClinic.exceptions.NoIdNumberException;
import com.ryszardpanda.medicalClinic.mapper.InstitutionMapper;
import com.ryszardpanda.medicalClinic.model.Doctor;
import com.ryszardpanda.medicalClinic.model.Institution;
import com.ryszardpanda.medicalClinic.model.InstitutionDTO;
import com.ryszardpanda.medicalClinic.repository.DoctorRepository;
import com.ryszardpanda.medicalClinic.repository.InstitutionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class InstitutionServiceTest {
    private DoctorRepository doctorRepository;
    private InstitutionRepository institutionRepository;
    private InstitutionMapper institutionMapper;
    private InstitutionService institutionService;

    @BeforeEach
    public void setUp(){
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.institutionRepository = Mockito.mock(InstitutionRepository.class);
        this.institutionMapper = Mappers.getMapper(InstitutionMapper.class);
        this.institutionService = new InstitutionService(doctorRepository,institutionRepository, institutionMapper);
    }

    @Test
    public void addInstitution_InstituionExist_InstitutionAdded(){
        InstitutionDTO institutionDTO = new InstitutionDTO(null, "Inst1");
        Institution institution = new Institution(1L, "Inst1", new HashSet<>());

        Institution mappedInstitution = institutionMapper.institutionDTOtoInstitution(institutionDTO);
        when(institutionRepository.save(any())).thenReturn(institution);

        Institution result = institutionService.addInstitution(institutionDTO);

        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Inst1", result.getName());
        Assertions.assertEquals(new HashSet<>(), result.getDoctors());
    }


    @Test
    public void findInstitutionById_InstitutionExist_InstitutionReturned(){
        //given
        Institution institution = new Institution(1L, "Inst1", new HashSet<>());

        //when
        when(institutionRepository.findById(institution.getId())).thenReturn(Optional.of(institution));

        Institution result = institutionService.findInstitutionById(institution.getId());

        //then
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Inst1", result.getName());
        Assertions.assertEquals(new HashSet<>(), result.getDoctors());
    }

    @Test
    public void findInstitutionById_InstitutionDoesNotExist_NoIdNumberException(){
        //given
        Institution institution = new Institution(1L, "Inst1", new HashSet<>());

        //when
        when(institutionRepository.findById(institution.getId())).thenReturn(Optional.empty());
        //then
        NoIdNumberException result = Assertions.assertThrows(NoIdNumberException.class, () -> institutionService
                .findInstitutionById(institution.getId()));

        Assertions.assertEquals("Nie znaleziono placówki o takim ID", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    public void assignDoctorToInstitution_InstitutionExist_InstitutionAssigned(){
        //given
        Doctor doctor = new Doctor(1L, "Bolek", "Lolek", "jsksks", "lkamdalsdm", "bolki", new HashSet<>());
        Institution institution = new Institution(1L, "Inst1", new HashSet<>());

        //when
        when(institutionRepository.findById(institution.getId())).thenReturn(Optional.of(institution));
        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));

        Institution result = institutionService.assignDoctorToInstitution(doctor.getId(), institution.getId());

        //then
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Inst1", result.getName());
        Assertions.assertEquals(institution.getDoctors(), result.getDoctors());
    }

    @Test
    public void assignDoctorToInstitution_InstitutionDoesentExist_InstitutionNotAssigned(){
        //given
        Doctor doctor = new Doctor(1L, "Bolek", "Lolek", "jsksks", "lkamdalsdm", "bolki", new HashSet<>());
        Institution institution = new Institution(1L, "Inst1", new HashSet<>());

        //when
        when(institutionRepository.findById(institution.getId())).thenReturn(Optional.empty());
        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));

        InstitutionNotFoundException result = Assertions.assertThrows(InstitutionNotFoundException.class, () -> institutionService
                .assignDoctorToInstitution(institution.getId(), doctor.getId()));
        //then
        Assertions.assertEquals("Nie znaleziono placówki o danym ID", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    public void assignDoctorToInstitution_DoctorDoesentExist_InstitutionNotAssigned(){
        //given
        Doctor doctor = new Doctor(1L, "Bolek", "Lolek", "jsksks", "lkamdalsdm", "bolki", new HashSet<>());
        Institution institution = new Institution(1L, "Inst1", new HashSet<>());

        //when
        when(institutionRepository.findById(institution.getId())).thenReturn(Optional.of(institution));
        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.empty());

        NoIdNumberException result = Assertions.assertThrows(NoIdNumberException.class, () -> institutionService
                .assignDoctorToInstitution(institution.getId(), doctor.getId()));
        //then
        Assertions.assertEquals("Nie znaleziono lekarza o danym ID", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }
}
