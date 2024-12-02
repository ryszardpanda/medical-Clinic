package com.ryszardpanda.medicalClinic.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitDTO {
    @NotBlank(message = "Data wizyty nie może być pusta, bądź być nullem")
    private LocalDateTime date;
}
