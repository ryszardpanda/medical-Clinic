package com.ryszardpanda.medicalClinic.service;

import com.ryszardpanda.medicalClinic.exceptions.EmailAlreadyInUse;
import com.ryszardpanda.medicalClinic.exceptions.NoIdNumberException;
import com.ryszardpanda.medicalClinic.exceptions.PersonIdAlreadyExist;
import com.ryszardpanda.medicalClinic.exceptions.PersonNotFoundException;
import com.ryszardpanda.medicalClinic.mapper.PatientMapper;
import com.ryszardpanda.medicalClinic.model.Patient;
import com.ryszardpanda.medicalClinic.model.PatientEditDTO;
import com.ryszardpanda.medicalClinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<Patient> getPatients() {
        return patientRepository.getPatients();
    }

    public Patient addPatient(PatientEditDTO patientEditDTO) {
        Patient patient = patientMapper.EditDtoToPatient(patientEditDTO);
        validatePatientFields(patient);
        patientRepository.getPatientByEmail(patient.getEmail()).ifPresent(existingPatient -> {
            throw new EmailAlreadyInUse("Pacjent o takim emailu już istnieje", HttpStatus.CONFLICT);
        });
            return patientRepository.addPatient(patient);
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.getPatientByEmail(email).orElseThrow(() -> new PersonNotFoundException("Nie znaleziono pacjenta o takim ID", HttpStatus.NOT_FOUND));
    }

    public boolean deletePatientByEmail(String email) {
        if (!patientRepository.deletePatientByEmail(email)) {
            throw new PersonNotFoundException("Nie znaleziono pacjenta o takim ID", HttpStatus.NOT_FOUND);
        }
        return patientRepository.deletePatientByEmail(email);
    }

    public Patient updatePatient(String email, PatientEditDTO updatedPatientEditDTO) {
        Patient updatedPatient = patientMapper.EditDtoToPatient(updatedPatientEditDTO);
        Patient patient = patientRepository.getPatientByEmail(email).orElseThrow(() -> new PersonNotFoundException("Nie znaleziono pacjenta o takim ID", HttpStatus.NOT_FOUND));
        if (updatedPatientEditDTO.getIdCardNo() != null
                && !patient.getIdCardNo().equals(updatedPatientEditDTO.getIdCardNo())) {
            throw new PersonIdAlreadyExist("Nie można zmienić istniejącego numeru dowodu, proszę wprowadzić poprzedni numer: " + patient.getIdCardNo(), HttpStatus.CONFLICT);
        }
        return patientRepository.updatePatient(email, updatedPatient).orElseThrow(() -> new PersonNotFoundException("Nie znaleziono użytkownika o takim ID", HttpStatus.NOT_FOUND));
    }

    public Patient updatePassword(String email, Patient updatedPassword) {
        return patientRepository.updatePassword(email, updatedPassword).orElseThrow(() -> new PersonNotFoundException("Nie znaleziono użytkownika o takim ID", HttpStatus.NOT_FOUND));
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