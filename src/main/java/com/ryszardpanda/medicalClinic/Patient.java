package com.ryszardpanda.medicalClinic;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor /// chce miec konstruktor do wsystkich argumentow
@Data // chce uzywac daty
public class Patient {
    private String name;
    private String surname;
    private String email;
}
