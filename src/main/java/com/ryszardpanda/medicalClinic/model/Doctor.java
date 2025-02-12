package com.ryszardpanda.medicalClinic.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "DOCTORS")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "DOCTOR_NAME", length = 50, nullable = false)
    private String firstName;
    @Column(name = "DOCTOR_LAST_NAME", length = 50, nullable = false)
    private String lastName;
    @Column(name = "DOCTOR_EMAIL", length = 50, nullable = false, unique = true)
    private String email;
    @Column(name = "DOCTOR_PASSWORD", length = 50, nullable = false)
    private String password;
    @Column(name = "SPECIALIZATION", length = 50, nullable = false)
    private String specialization;

    @ManyToMany(mappedBy = "doctors", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Institution> institutions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Doctor))
            return false;

        Doctor other = (Doctor) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
