package com.ryszardpanda.medicalClinic.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class PatientDTO {
    private long id;
    @NotBlank(message = "Imie nie może być puste, bądź być nullem")
    private String firstName;
    @NotBlank(message = "Nazwisko nie może być puste, bądź być nullem")
    private String lastName;
    @Email(message = "Zły format email")
    @NotBlank(message = "Email nie może być puste, bądź być nullem")
    private String email;
    private String idCardNo;
    @NotBlank(message = "Telefon nie może być pusty, bądź być nullem")
    private String phoneNumber;
    @NotNull(message = "Data nie może być pusta, bądź być nullem")
    private LocalDate birthday;
    private String fullName;
}
