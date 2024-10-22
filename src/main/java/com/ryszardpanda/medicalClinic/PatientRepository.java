package com.ryszardpanda.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PatientRepository {

    private final List<Patient> patients;

    public List<Patient> getPatients() {
        return new ArrayList<>(patients);
    }

    public Patient addPatient(Patient patient) {
        patients.add(patient);
        return patient;
    }

    public Optional<Patient> getPatientByEmail(String email) {
        // Zakładam, że email jest unikalny, więc szukamy pierwszego pasującego
        return patients.stream()
                .filter(patient -> patient.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public boolean deletePatientByEmail(String email) {
        return patients.removeIf(patient -> patient.getEmail().equalsIgnoreCase(email));
    }

    public Patient updatePatient(String email, Patient updatedPatient) {
        Optional<Patient> optionalPatient = getPatientByEmail(email);
        optionalPatient.ifPresent(patient -> {
            patient.setName(updatedPatient.getName());
            patient.setSurname(updatedPatient.getSurname());
            patient.setEmail(updatedPatient.getEmail());
        });
        return optionalPatient.orElse(null);
    }
}