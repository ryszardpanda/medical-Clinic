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
    @Column(name = "dateOfVisit", length = 50, nullable = false)
    private LocalDateTime date;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    public static Visit create(Doctor doctor, LocalDateTime date){
        Visit visit = new Visit();
        visit.setDoctor(doctor);
        visit.setDate(date);
        return visit;
    }
}
