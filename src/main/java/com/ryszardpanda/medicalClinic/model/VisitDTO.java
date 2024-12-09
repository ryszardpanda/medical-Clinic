package com.ryszardpanda.medicalClinic.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitDTO {

    private Long id;
    private LocalDateTime date;
    private Long doctorId;
    private Long institutionId;
}
