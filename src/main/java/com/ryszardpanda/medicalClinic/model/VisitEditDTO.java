package com.ryszardpanda.medicalClinic.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class VisitEditDTO {

    @NotNull(message = "Id nie może być nullem")
    private Long id;
    @NotNull(message = "Data nie może być pusta, bądź być nullem")
    @Future(message = "Date and time must be in the future")
    private LocalDateTime date;
    @NotNull
    private Long doctorId;
    @NotNull
    private Long institutionId;
}
