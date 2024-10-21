package com.ryszardpanda.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getPatients() {
        return patientRepository.getPatients();
    }

    public List<Patient> addPatients(List<Patient> patients) {
        return patients.stream()
                .map(patientRepository::addPatient)
                .collect(Collectors.toList());
    }

    public Patient getPatientByEmail( String email) {
        return patientRepository.getPatientByEmail(email);
    }

    public boolean deletePatientByEmail(String email) {
        return patientRepository.deletePatientByEmail(email); // Wywołanie metody z repozytorium
    }

    public Patient updatePatient(String email, Patient updatedPatient) {
        return patientRepository.updatePatient(email, updatedPatient); // Wywołanie metody z repozytorium
    }

}
