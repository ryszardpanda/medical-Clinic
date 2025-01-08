package com.ryszardpanda.medicalClinic.service;

import com.ryszardpanda.medicalClinic.exceptions.NoIdNumberException;
import com.ryszardpanda.medicalClinic.exceptions.VisitUnavailable;
import com.ryszardpanda.medicalClinic.model.*;
import com.ryszardpanda.medicalClinic.repository.VisitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VisitServiceTest {
    private VisitRepository visitRepository;
    private PatientService patientService;
    private DoctorService doctorService;
    private InstitutionService institutionService;
    private VisitService visitService;

    @BeforeEach
    void setUp(){
        this.visitRepository = Mockito.mock(VisitRepository.class);
        this.patientService = Mockito.mock(PatientService.class);
        this.doctorService = Mockito.mock(DoctorService.class);
        this.institutionService = Mockito.mock(InstitutionService.class);
        this.visitService = new VisitService(visitRepository,patientService, doctorService, institutionService);
    }

    @Test
    public void createVisit_DoctorAndInstitutionExist_VisitCreated(){
        //given

        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());
        Institution institution = new Institution(1L, "Marcinkowska", new HashSet<Doctor>());
        VisitEditDTO visitEditDTO = new VisitEditDTO(1L, LocalDateTime.of(2025, 2, 12, 11, 30),
                LocalDateTime.of(2025, 2, 12, 11, 45),
                doctor.getId(), institution.getId());

       Visit visit = new Visit(1L, LocalDateTime.of(2025, 2, 12, 11, 30),
                LocalDateTime.of(2025, 2, 12, 11, 45), new Patient(), doctor, institution);

        when(doctorService.findDoctorById(1L)).thenReturn(doctor);
        when(institutionService.findInstitutionById(1L)).thenReturn(institution);
        when(visitRepository.findOverlappingVisits(doctor.getId(), visitEditDTO.getStartDate(), visitEditDTO.getEndDate())).thenReturn(new ArrayList<>());
        when(visitRepository.save(any())).thenReturn(visit);

        //when
        Visit result = visitService.createVisit(visitEditDTO);
        //then
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals(LocalDateTime.of(2025, 2, 12, 11, 30), result.getStartDate());
        Assertions.assertEquals(LocalDateTime.of(2025, 2, 12, 11, 45), result.getEndDate());
        Assertions.assertEquals(LocalDateTime.of(2025, 2, 12, 11, 45), result.getEndDate());
        Assertions.assertEquals(1L, result.getDoctor().getId());
        Assertions.assertEquals(1L, result.getInstitution().getId());
    }

    @Test
    public void createVisit_VisitOverlaping_VisitNotCreated(){
        //given
        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());
        Institution institution = new Institution(1L, "Marcinkowska", new HashSet<Doctor>());
        VisitEditDTO visitEditDTO = new VisitEditDTO(1L, LocalDateTime.of(2025, 2, 12, 11, 30),
                LocalDateTime.of(2025, 2, 12, 11, 45),
                doctor.getId(), institution.getId());

        Visit visit = new Visit(1L, LocalDateTime.of(2025, 2, 12, 11, 30),
                LocalDateTime.of(2025, 2, 12, 11, 45), new Patient(), doctor, institution);


        when(doctorService.findDoctorById(1L)).thenReturn(doctor);
        when(institutionService.findInstitutionById(1L)).thenReturn(institution);
        when(visitRepository.findOverlappingVisits(doctor.getId(), visitEditDTO.getStartDate(), visitEditDTO.getEndDate())).thenReturn(List.of(visit));
        //when
        VisitUnavailable result = Assertions.assertThrows(VisitUnavailable.class, () -> visitService.createVisit(visitEditDTO));
        //then
        Assertions.assertEquals("Wizyta nakłada się na inną wizytę. Wybierz inny termin", result.getMessage());
        Assertions.assertEquals(HttpStatus.CONFLICT, result.getHttpStatus());
    }

    @Test
    public void createVisit_NonTimeInQuearters_VisitNotCreated(){
        //given
        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());
        Institution institution = new Institution(1L, "Marcinkowska", new HashSet<Doctor>());
        VisitEditDTO visitEditDTO = new VisitEditDTO(1L, LocalDateTime.of(2025, 2, 12, 11, 30),
                LocalDateTime.of(2025, 2, 12, 11, 41),
                doctor.getId(), institution.getId());

        Visit visit = new Visit(1L, LocalDateTime.of(2025, 2, 12, 11, 30),
                LocalDateTime.of(2025, 2, 12, 11, 41), new Patient(), doctor, institution);


        when(doctorService.findDoctorById(1L)).thenReturn(doctor);
        when(institutionService.findInstitutionById(1L)).thenReturn(institution);
        //when
        VisitUnavailable result = Assertions.assertThrows(VisitUnavailable.class, () -> visitService.checkVisit(visitEditDTO, doctor));
        //then
        Assertions.assertEquals("Godzina wizyty musi być ustawiona na pełne kwadranse (np. 13:00, 13:15, 13:30, 13:45).", result.getMessage());
        Assertions.assertEquals(HttpStatus.CONFLICT, result.getHttpStatus());
    }

    @Test
    public void bookVisit_visitCanBeBooked_VisitBooked(){
        //given
        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());
        Institution institution = new Institution(1L, "Marcinkowska", new HashSet<Doctor>());
        Patient patient = new Patient(1L, "Kamil", "Kamiliski", "eloelo@op.pl", "elloo", "123", "121212122", LocalDate.of(2222, 11, 11));
        Visit visit = new Visit(1L, LocalDateTime.of(2025, 2, 12, 11, 30),
                LocalDateTime.of(2025, 2, 12, 11, 45), null, doctor, institution);

        //when
        when(visitRepository.findById(visit.getId())).thenReturn(Optional.of(visit));
        when(patientService.findPatientById(1L)).thenReturn(patient);

        //then
        Visit result = visitService.bookVisit(visit.getId(), patient.getId());

        Assertions.assertEquals(visit.getId(), result.getId());
        Assertions.assertEquals(patient, result.getPatient());
        Assertions.assertEquals(doctor, result.getDoctor());
        Assertions.assertEquals(institution, result.getInstitution());
        Assertions.assertEquals(LocalDateTime.of(2025, 2, 12, 11, 30), result.getStartDate());
        Assertions.assertEquals(LocalDateTime.of(2025, 2, 12, 11, 45), result.getEndDate());
    }

    @Test
    public void bookVisit_visitDoesentExist_VisitNotBooked() {
        //given
        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());
        Institution institution = new Institution(1L, "Marcinkowska", new HashSet<Doctor>());
        Patient patient = new Patient(1L, "Kamil", "Kamiliski", "eloelo@op.pl", "elloo", "123", "121212122", LocalDate.of(2222, 11, 11));
        Visit visit = new Visit(1L, LocalDateTime.of(2025, 2, 12, 11, 30),
                LocalDateTime.of(2025, 2, 12, 11, 45), null, doctor, institution);

        //when
        when(visitRepository.findById(visit.getId())).thenReturn(Optional.empty());

        //then
        NoIdNumberException result = Assertions.assertThrows(NoIdNumberException.class, () -> visitService.bookVisit(visit.getId(), patient.getId()));

        Assertions.assertEquals("Wizyta o takim ID nie istnieje", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    public void bookVisit_visitPassed_VisitNotBooked() {
        //given
        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());
        Institution institution = new Institution(1L, "Marcinkowska", new HashSet<Doctor>());
        Patient patient = new Patient(1L, "Kamil", "Kamiliski", "eloelo@op.pl", "elloo", "123", "121212122", LocalDate.of(2222, 11, 11));
        Visit visit = new Visit(1L, LocalDateTime.of(2024, 2, 12, 11, 30),
                LocalDateTime.of(2025, 2, 12, 11, 45), null, doctor, institution);

        //when
        when(visitRepository.findById(visit.getId())).thenReturn(Optional.of(visit));

        //then
        VisitUnavailable result = Assertions.assertThrows(VisitUnavailable.class, () -> visitService.bookVisit(visit.getId(), patient.getId()));

        Assertions.assertEquals("Nie można zarezerwować przeszłej wizyty", result.getMessage());
        Assertions.assertEquals(HttpStatus.CONFLICT, result.getHttpStatus());
    }

    @Test
    public void bookVisit_visitAlreadyBooked_VisitNotBooked() {
        //given
        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());
        Institution institution = new Institution(1L, "Marcinkowska", new HashSet<Doctor>());
        Patient patient = new Patient(1L, "Kamil", "Kamiliski", "eloelo@op.pl", "elloo", "123", "121212122", LocalDate.of(2222, 11, 11));
        Visit visit = new Visit(1L, LocalDateTime.of(2025, 2, 12, 11, 30),
                LocalDateTime.of(2025, 2, 12, 11, 45), new Patient(), doctor, institution);

        //when
        when(visitRepository.findById(visit.getId())).thenReturn(Optional.of(visit));

        //then
        VisitUnavailable result = Assertions.assertThrows(VisitUnavailable.class, () -> visitService.bookVisit(visit.getId(), patient.getId()));

        Assertions.assertEquals("Ta data jest zajęta, nie można zarezerwować wizyty", result.getMessage());
        Assertions.assertEquals(HttpStatus.CONFLICT, result.getHttpStatus());
    }

    @Test
    void testGetPatientsVisits_VisitsExist_ReturnListOfVisits() {
        // given
        Long patientId = 123L;
        Visit visit1 = new Visit(1L, LocalDateTime.of(2030, 5, 1, 10, 0),
                LocalDateTime.of(2030, 5, 1, 10, 15), null, null, null);
        Visit visit2 = new Visit(2L, LocalDateTime.of(2030, 5, 2, 12, 0),
                LocalDateTime.of(2030, 5, 2, 12, 15), null, null, null);

        List<Visit> expectedVisits = List.of(visit1, visit2);

        when(visitRepository.findByPatientId(patientId)).thenReturn(expectedVisits);

        // when
        List<Visit> actualVisits = visitService.getPatientsVisits(patientId);

        // then
        Assertions.assertEquals(expectedVisits, actualVisits);

        verify(visitRepository, times(1)).findByPatientId(patientId);
    }

    @Test
    void GetAvailableVisit_VisitExist_ReturneListOfAvailableVisits() {
        // given
        Visit visitA = new Visit(10L, LocalDateTime.of(2030, 6, 1, 10, 0),
                LocalDateTime.of(2030, 6, 1, 10, 15), null, null, null);
        Visit visitB = new Visit(11L, LocalDateTime.of(2030, 6, 1, 11, 0),
                LocalDateTime.of(2030, 6, 1, 11, 15), null, null, null);

        List<Visit> availableVisits = List.of(visitA, visitB);

        when(visitRepository.findAvailableVisits()).thenReturn(availableVisits);

        // when
        List<Visit> result = visitService.getAvailableVisit();

        // then
        Assertions.assertEquals(availableVisits, result);
        verify(visitRepository, times(1)).findAvailableVisits();
    }
}
