package com.ryszardpanda.medicalClinic.service;

import com.ryszardpanda.medicalClinic.exceptions.NoIdNumberException;
import com.ryszardpanda.medicalClinic.exceptions.VisitUnavailable;
import com.ryszardpanda.medicalClinic.model.*;
import com.ryszardpanda.medicalClinic.repository.VisitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final InstitutionService institutionService;

    @Transactional
    public Visit createVisit(VisitEditDTO visitEditDTO) {
        Visit visit = checkVisit(visitEditDTO);
        return visitRepository.save(visit);
    }

    @Transactional
    public Visit bookVisit(Long visitId, Long patientId) {
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new NoIdNumberException("Wizyta o takim ID nie istnieje",
                HttpStatus.NOT_FOUND));

        if (visit.getStartDate().isBefore(LocalDateTime.now())) {
            throw new VisitUnavailable("Nie można zarezerwować przeszłej wizyty", HttpStatus.CONFLICT);
        }
        if (visit.getPatient() != null) {
            throw new VisitUnavailable("Ta data jest zajęta, nie można zarezerwować wizyty", HttpStatus.CONFLICT);
        }
        Patient patientById = patientService.findPatientById(patientId);
        visit.setPatient(patientById);
        return visit;
    }

    public List<Visit> getPatientsVisits(Long patientId) {
        return visitRepository.findByPatientId(patientId);
    }

    public List<Visit> getAvailableVisit() {
        return visitRepository.findAvailableVisits();
    }

    public Visit checkVisit(VisitEditDTO visitEditDTO) {

        Doctor doctor = doctorService.findDoctorById(visitEditDTO.getDoctorId());
        Institution institution = institutionService.findInstitutionById(visitEditDTO.getInstitutionId());

        List<Visit> overlappingVisits = visitRepository.findOverlappingVisits(
                doctor.getId(),
                visitEditDTO.getStartDate(),
                visitEditDTO.getEndDate()
        );

        if (!overlappingVisits.isEmpty()) {
            throw new VisitUnavailable("Wizyta nakłada się na inną wizytę. Wybierz inny termin", HttpStatus.CONFLICT);
        }

        Visit visit = new Visit();
        visit.setDoctor(doctor);
        visit.setInstitution(institution);
        visit.setStartDate(visitEditDTO.getStartDate());
        visit.setEndDate(visitEditDTO.getEndDate());

        return visit;
    }
}
