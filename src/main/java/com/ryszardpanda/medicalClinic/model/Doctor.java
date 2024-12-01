package com.ryszardpanda.medicalClinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
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

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;
}
