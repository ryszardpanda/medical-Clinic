package com.ryszardpanda.medicalClinic.mapper;

import com.ryszardpanda.medicalClinic.model.Patient;
import com.ryszardpanda.medicalClinic.model.PatientDTO;

public class PatientMapper {
    public static PatientDTO patientToDTO(Patient patient) {
        return new PatientDTO(patient.getFirstName(), patient.getLastName(), patient.getEmail(),
                patient.getIdCardNo(), patient.getPhoneNumber(), patient.getBirthday());
    }
}
