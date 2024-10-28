package com.ryszardpanda.medicalClinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class PatientDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String idCardNo;
    private String phoneNumber;
    private LocalDate birthday;
}
