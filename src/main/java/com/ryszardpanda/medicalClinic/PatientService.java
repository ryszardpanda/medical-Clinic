package com.ryszardpanda.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getPatients() {
        return patientRepository.getPatients();
    }

    public Patient addPatient(Patient patients) {
        return patientRepository.addPatient(patients);
    }

    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.getPatientByEmail(email);
    }

    public boolean deletePatientByEmail(String email) {
        return patientRepository.deletePatientByEmail(email); // Wywołanie metody z repozytorium
    }

    public Patient updatePatient(String email, Patient updatedPatient) {
        return patientRepository.updatePatient(email, updatedPatient); // Wywołanie metody z repozytorium
    }

}
