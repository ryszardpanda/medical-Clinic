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
        Doctor doctor = doctorService.findDoctorById(visitEditDTO.getDoctorId());
        Institution institution = institutionService.findInstitutionById(visitEditDTO.getInstitutionId());
        checkVisit(visitEditDTO, doctor);
        Visit visit = Visit.of(visitEditDTO, doctor, institution);
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

     void checkVisit(VisitEditDTO visitEditDTO, Doctor doctor) {

        validateTime(visitEditDTO.getStartDate());
        validateTime(visitEditDTO.getEndDate());

        List<Visit> overlappingVisits = visitRepository.findOverlappingVisits(
                doctor.getId(),
                visitEditDTO.getStartDate(),
                visitEditDTO.getEndDate()
        );

        if (!overlappingVisits.isEmpty()) {
            throw new VisitUnavailable("Wizyta nakłada się na inną wizytę. Wybierz inny termin", HttpStatus.CONFLICT);
        }
    }

    private void validateTime(LocalDateTime dateTime) {
        int minutes = dateTime.getMinute();
        if (minutes % 15 != 0) {
            throw new VisitUnavailable("Godzina wizyty musi być ustawiona na pełne kwadranse (np. 13:00, 13:15, 13:30, 13:45).",
                    HttpStatus.CONFLICT);
        }
    }
}
