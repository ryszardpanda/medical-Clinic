package com.ryszardpanda.medicalClinic.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class VisitEditDTO {

    private Long id;
    @NotNull(message = "Data nie może być pusta, bądź być nullem")
    @Future(message = "Data i czas muszą być w przyszłości")
    private LocalDateTime startDate;
    @Future(message = "Data i czas muszą być w przyszłości")
    private LocalDateTime endDate;
    @NotNull(message = "Id lekarza, nie może być nullem")
    private Long doctorId;
    @NotNull(message = "Id instytucji, nie może być nullem")
    private Long institutionId;
}
