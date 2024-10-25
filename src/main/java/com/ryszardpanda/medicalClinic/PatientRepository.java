package com.ryszardpanda.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository // służy do oznaczania komponentów, które pełnią rolę warstwy dostępu do danych
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

    public Optional<Patient> updatePatient(String email, Patient updatedPatient) {
        Optional<Patient> optionalPatient = getPatientByEmail(email);
        optionalPatient.ifPresent(patient -> {
            patient.setFirstName(updatedPatient.getFirstName());
            patient.setLastName(updatedPatient.getLastName());
            patient.setEmail(updatedPatient.getEmail());
            patient.setPassword(updatedPatient.getPassword());
            patient.setBirthday(updatedPatient.getBirthday());
            patient.setIdCardNo(updatedPatient.getIdCardNo());
            patient.setPhoneNumber(updatedPatient.getPhoneNumber());
        });
        return optionalPatient;
    }

    public Optional<Patient> updatePassword(String email, Patient updatedPassword) {
        Optional<Patient> optionalPatient = getPatientByEmail(email);
        optionalPatient.ifPresent(patient -> {
            patient.setPassword(updatedPassword.getPassword());
        });
        return optionalPatient;
    }
}