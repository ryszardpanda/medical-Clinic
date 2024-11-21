package com.ryszardpanda.medicalClinic.service;

import com.ryszardpanda.medicalClinic.model.Patient;
import com.ryszardpanda.medicalClinic.repository.PatientDAORepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceForDAO {

    private PatientDAORepository patientDAORepository;

    // Pobierz wszystkich pacjentów
    public List<Patient> getPatients() {
        return patientDAORepository.findAll();
    }

    // Dodaj nowego pacjenta
    public Patient addPatient(Patient patient) {
        Optional<Patient> existingPatient = patientDAORepository.findByEmail(patient.getEmail());
        if (existingPatient.isPresent()) {
            throw new IllegalArgumentException("Pacjent z tym adresem email już istnieje.");
        }
        return patientDAORepository.save(patient);
    }

    // Zaktualizuj dane pacjenta
    public Optional<Patient> updatePatient(String email, Patient updatedPatient) {
        Optional<Patient> patientOptional = patientDAORepository.findByEmail(email);

        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            // Aktualizujemy dane pacjenta
            patient.setFirstName(updatedPatient.getFirstName());
            patient.setLastName(updatedPatient.getLastName());
            patient.setIdCardNo(updatedPatient.getIdCardNo());
            patient.setPhoneNumber(updatedPatient.getPhoneNumber());
            patient.setBirthday(updatedPatient.getBirthday());
            // Zapisujemy zmiany
            Patient savedPatient = patientDAORepository.save(patient);
            return Optional.of(savedPatient);
        }

        return Optional.empty();
    }

    // Zaktualizuj hasło pacjenta
    public void updatePassword(String email, String newPassword) {
        patientDAORepository.updatePasswordByEmail(email, newPassword);
    }
}
