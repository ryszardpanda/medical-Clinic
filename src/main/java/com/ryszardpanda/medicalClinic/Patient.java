package com.ryszardpanda.medicalClinic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor /// chce miec konstruktor do wsystkich argumentow
@Data // chce uzywac daty
public class Patient {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String idCardNo;
    private String phoneNumber;
    private LocalDate birthday;
}
