package com.ryszardpanda.medicalClinic.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InstitutionDTO {
    @NotBlank(message = "Nazwa nie może być pusta, bądź być nullem")
    private String name;
}
