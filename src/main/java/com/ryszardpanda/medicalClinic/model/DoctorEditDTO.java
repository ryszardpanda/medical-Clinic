package com.ryszardpanda.medicalClinic.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DoctorEditDTO {
    @NotBlank(message = "Imie nie może być puste, bądź być nullem")
    private String firstName;
    @NotBlank(message = "Nazwisko nie może być puste, bądź być nullem")
    private String lastName;
    @Email(message = "Zły format email")
    @NotBlank(message = "Email nie może być puste, bądź być nullem")
    private String email;
    private String password;
    @NotBlank(message = "Specjalizacja nie może być pusta, bądź być nullem")
    private String specialization;
}
