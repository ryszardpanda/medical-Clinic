package com.ryszardpanda.medicalClinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Visit")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "startDate", length = 50, nullable = false)
    private LocalDateTime startDate;
    @Column(name = "endDate", length = 50, nullable = false)
    private LocalDateTime endDate;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Institution institution;

    public static Visit of(VisitEditDTO visitEditDTO, Doctor doctor, Institution institution){
        Visit visit = new Visit();
        visit.setDoctor(doctor);
        visit.setInstitution(institution);
        visit.setStartDate(visitEditDTO.getStartDate());
        visit.setEndDate(visitEditDTO.getEndDate());

        return visit;
    }
}
