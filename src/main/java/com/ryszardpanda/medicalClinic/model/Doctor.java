package com.ryszardpanda.medicalClinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Doctor {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String specialization;
}
