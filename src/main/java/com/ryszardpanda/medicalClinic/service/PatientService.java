package com.ryszardpanda.medicalClinic.service;

import com.ryszardpanda.medicalClinic.exceptions.PatientNotFoundException;
import com.ryszardpanda.medicalClinic.model.Patient;
import com.ryszardpanda.medicalClinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //przechowują logikę aplikacji
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getPatients() {
        return patientRepository.getPatients();
    }

    public Patient addPatient(Patient patients) {
        return patientRepository.addPatient(patients);
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.getPatientByEmail(email).orElseThrow(() -> new PatientNotFoundException("Nie znaleziono użytkownika o takim ID", HttpStatus.NOT_FOUND));
    }

    public boolean deletePatientByEmail(String email) {
        if (!patientRepository.deletePatientByEmail(email)){
            throw new PatientNotFoundException("Nie znaleziono użytkownika o takim ID", HttpStatus.NOT_FOUND);
        }
        return patientRepository.deletePatientByEmail(email); // Wywołanie metody z repozytorium
    }

    public Patient updatePatient(String email, Patient updatedPatient) {
        return patientRepository.updatePatient(email, updatedPatient).orElseThrow(() -> new PatientNotFoundException("Nie znaleziono użytkownika o takim ID", HttpStatus.NOT_FOUND));  // Wywołanie metody z repozytorium
    }

    public Patient updatePassword(String email, Patient updatedPassword) {
        return patientRepository.updatePassword(email, updatedPassword).orElseThrow(() -> new PatientNotFoundException("Nie znaleziono użytkownika o takim ID", HttpStatus.NOT_FOUND));  // Wywołanie metody z repozytorium
    }
}
