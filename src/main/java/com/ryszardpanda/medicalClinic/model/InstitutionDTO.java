package com.ryszardpanda.medicalClinic.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InstitutionDTO {
    @NotNull(message = "Id nie może być nullem")
    private Long id;
    @NotBlank(message = "Nazwa nie może być pusta, bądź być nullem")
    private String name;
}
