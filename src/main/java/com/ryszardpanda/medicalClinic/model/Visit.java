package com.ryszardpanda.medicalClinic.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @ManyToOne(cascade = {CascadeType.ALL})
    private Patient patient;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Doctor doctor;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Institution institution;

    public static Visit of(VisitEditDTO visitEditDTO, Doctor doctor, Institution institution){
        Visit visit = new Visit();
        visit.setDoctor(doctor);
        visit.setInstitution(institution);
        visit.setStartDate(visitEditDTO.getStartDate());
        visit.setEndDate(visitEditDTO.getEndDate());

        return visit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Visit))
            return false;

        Visit other = (Visit) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
