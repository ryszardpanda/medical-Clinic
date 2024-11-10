package com.ryszardpanda.medicalClinic.service;

import com.ryszardpanda.medicalClinic.exceptions.EmailAlreadyInUse;
import com.ryszardpanda.medicalClinic.exceptions.NoIdNumberException;
import com.ryszardpanda.medicalClinic.exceptions.PatientIdAlreadyExist;
import com.ryszardpanda.medicalClinic.exceptions.PatientNotFoundException;
import com.ryszardpanda.medicalClinic.model.Patient;
import com.ryszardpanda.medicalClinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getPatients() {
        return patientRepository.getPatients();
    }

    public Patient addPatient(Patient patient) {
        validatePatientFields(patient);
        patientRepository.getPatientByEmail(patient.getEmail()).ifPresent(existingPatient -> {
            throw new EmailAlreadyInUse("Pacjent o takim emailu już istnieje", HttpStatus.CONFLICT);
        });
            return patientRepository.addPatient(patient);
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.getPatientByEmail(email).orElseThrow(() -> new PatientNotFoundException("Nie znaleziono użytkownika o takim ID", HttpStatus.NOT_FOUND));
    }

    public boolean deletePatientByEmail(String email) {
        if (!patientRepository.deletePatientByEmail(email)) {
            throw new PatientNotFoundException("Nie znaleziono użytkownika o takim ID", HttpStatus.NOT_FOUND);
        }
        return patientRepository.deletePatientByEmail(email);
    }

    public Patient updatePatient(String email, Patient updatedPatient) {
        Patient patient = patientRepository.getPatientByEmail(email).orElseThrow(() -> new PatientNotFoundException("Nie znaleziono użytkownika o takim ID", HttpStatus.NOT_FOUND));
        if (!patient.getIdCardNo().equals(updatedPatient.getIdCardNo())) {
            throw new PatientIdAlreadyExist("Nie można zmienić istniejącego numeru dowodu", HttpStatus.CONFLICT);
        }
        return patientRepository.updatePatient(email, updatedPatient).orElseThrow(() -> new PatientNotFoundException("Nie znaleziono użytkownika o takim ID", HttpStatus.NOT_FOUND));
    }

    public Patient updatePassword(String email, Patient updatedPassword) {
        return patientRepository.updatePassword(email, updatedPassword).orElseThrow(() -> new PatientNotFoundException("Nie znaleziono użytkownika o takim ID", HttpStatus.NOT_FOUND));
    }

    private void validatePatientFields(Patient patient) {
        if (patient.getFirstName() == null || patient.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("Imię nie może być puste lub nullem");
        }
        if (patient.getLastName() == null || patient.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nazwisko nie może być puste lub nullem");
        }
        if (patient.getEmail() == null || patient.getEmail().trim().isEmpty() || !patient.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email jest niepoprawny");
        }
        if (patient.getIdCardNo() == null || patient.getIdCardNo().trim().isEmpty()) {
            throw new NoIdNumberException("Numer dowodu nie może być pusty lub nullem", HttpStatus.NOT_FOUND);
        }
        if (patient.getPhoneNumber() == null || patient.getPhoneNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Numer telefonu nie może być pusty lub nullem");
        }
        if (patient.getBirthday() == null) {
            throw new IllegalArgumentException("Data urodzenia nie może być pusta lub nullem");
        }
    }
}
