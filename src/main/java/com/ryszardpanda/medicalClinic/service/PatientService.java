package com.ryszardpanda.medicalClinic.service;

import com.ryszardpanda.medicalClinic.exceptions.EmailAlreadyInUse;
import com.ryszardpanda.medicalClinic.exceptions.PersonNotFoundException;
import com.ryszardpanda.medicalClinic.mapper.PatientMapper;
import com.ryszardpanda.medicalClinic.model.Patient;
import com.ryszardpanda.medicalClinic.model.PatientEditDTO;
import com.ryszardpanda.medicalClinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    final private PatientRepository patientRepository;
    final private PatientMapper patientMapper;

    // Pobierz wszystkich pacjentów
    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    // Dodaj nowego pacjenta
    public Patient addPatient(PatientEditDTO patientEditDTO) {
        Patient patient = patientMapper.patientEditDtoToPatient(patientEditDTO);
        validatePatientFields(patient);
        Optional<Patient> existingPatient = patientRepository.findByEmail(patientEditDTO.getEmail());
        if (existingPatient.isPresent()) {
            throw new EmailAlreadyInUse("Pacjent z tym adresem email już istnieje.", HttpStatus.CONFLICT);
        }
        return patientRepository.save(patient);
    }

    // Zaktualizuj dane pacjenta
    public Patient updatePatient(String email, PatientEditDTO updatedPatientEditDTO) {
        Patient updatedPatient = patientMapper.patientEditDtoToPatient(updatedPatientEditDTO);
        Patient patient = patientRepository.findByEmail(email).orElseThrow(() -> new PersonNotFoundException("Nie znaleziono pacjenta o takim ID",
                HttpStatus.NOT_FOUND));
        // Aktualizujemy dane pacjenta
        patient.setFirstName(updatedPatient.getFirstName());
        patient.setLastName(updatedPatient.getLastName());
        patient.setIdCardNo(updatedPatient.getIdCardNo());
        patient.setPhoneNumber(updatedPatient.getPhoneNumber());
        patient.setBirthday(updatedPatient.getBirthday());
        // Zapisujemy zmiany
        return patientRepository.save(patient);
    }

    // Zaktualizuj hasło pacjenta
    public Patient updatePassword(String email, String newPassword) {
        Patient patient = patientRepository.findByEmail(email).orElseThrow(() -> new PersonNotFoundException("Nie znaleziono Pacjenta o takim ID",
                HttpStatus.NOT_FOUND));
        patient.setPassword(newPassword);
        return patientRepository.save(patient);
    }

    public void deletePatientByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email).orElseThrow(() -> new PersonNotFoundException("Nie znaleziono Pacjenta o takim ID",
                HttpStatus.NOT_FOUND));
        patientRepository.delete(patient);
    }

    public Patient getPatientByEmail(String email) {
        return patientRepository.findByEmail(email).orElseThrow(() -> new PersonNotFoundException("Nie znaleziono Pacjenta o takim emailu.",
                HttpStatus.NOT_FOUND));

    }

    public void validatePatientFields(Patient patient) {
        if (patient.getFirstName() == null || patient.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("Imie nie może być puste, lub być nullem");
        }
        if (patient.getLastName() == null || patient.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Nazwisko nie może być nullem lub być puste");
        }
        if (patient.getEmail() == null || patient.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email nie może być pusty, lub być nullem");
        }
        if (patient.getPassword() == null || patient.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Hasło nie może być nullem lub być puste");
        }
        if (patient.getIdCardNo() == null || patient.getIdCardNo().isEmpty()) {
            throw new IllegalArgumentException("Dowód nie może być puste, lub być nullem");
        }
        if (patient.getPhoneNumber() == null || patient.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Numer telefonu nie może być pusty");
        }
        if (patient.getBirthday() == null) {
            throw new IllegalArgumentException("Data urodzenia nie może być pusta");
        }
    }
}
