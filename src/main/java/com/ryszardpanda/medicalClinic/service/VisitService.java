package com.ryszardpanda.medicalClinic.service;

import com.ryszardpanda.medicalClinic.exceptions.NoIdNumberException;
import com.ryszardpanda.medicalClinic.exceptions.VisitUnavailable;
import com.ryszardpanda.medicalClinic.model.Doctor;
import com.ryszardpanda.medicalClinic.model.Patient;
import com.ryszardpanda.medicalClinic.model.Visit;
import com.ryszardpanda.medicalClinic.repository.VisitRepository;
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

    public Visit createVisit(Long doctorId, LocalDateTime dateTime) {
        Doctor doctorById = doctorService.findDoctorById(doctorId);
        return visitRepository.save(Visit.create(doctorById, dateTime));
    }

    public Visit bookVisit(Long visitId, Long patientId) {
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new NoIdNumberException("Wizyta o takim ID nie istnieje",
                HttpStatus.NOT_FOUND));

        if (visit.getDate().isBefore(LocalDateTime.now())) {
            throw new VisitUnavailable("Nie można zarezerwować przeszłej wizyty", HttpStatus.CONFLICT);
        }
        if (visit.getPatient() != null) {
            throw new VisitUnavailable("Ta data jest zajęta, nie można zarezerwować wizyty", HttpStatus.CONFLICT);
        }
        Patient patientById = patientService.findPatientById(patientId);
        visit.setPatient(patientById);
        return visitRepository.save(visit);
    }

    public List<Visit> getPatientsVisits(Long patientId) {
        return visitRepository.findByPatientId(patientId);
    }

    public List<Visit> getAvailableVisit() {
        return visitRepository.findAvailableVisits();
    }
}
