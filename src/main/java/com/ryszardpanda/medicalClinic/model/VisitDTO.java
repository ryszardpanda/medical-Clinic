package com.ryszardpanda.medicalClinic.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitDTO {

    private Long id;
    @NotNull(message = "Data nie może być pusta, bądź być nullem")
    private LocalDateTime date;
}
