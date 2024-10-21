package com.ryszardpanda.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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

    public Patient getPatientByEmail(String email) {
        // Zakładam, że email jest unikalny, więc szukamy pierwszego pasującego
        return patients.stream()
                .filter(patient -> patient.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null); // Zwraca null, jeśli pacjent nie został znaleziony
    }

    public boolean deletePatientByEmail(String email) {
        return patients.removeIf(patient -> patient.getEmail().equalsIgnoreCase(email));
    }

    public Patient updatePatient(String email, Patient updatedPatient) {
        for (Patient patient : patients) {
            if (patient.getEmail().equalsIgnoreCase(email)) {
                // Aktualizacja danych pacjenta
                patient.setName(updatedPatient.getName());
                patient.setSurname(updatedPatient.getSurname());
                patient.setEmail(updatedPatient.getEmail());
                return patient; // Zwraca zaktualizowanego pacjenta
            }
        }
        return null; // Jeśli pacjent nie został znaleziony, zwraca null
    }
}