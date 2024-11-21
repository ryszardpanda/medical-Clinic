package com.ryszardpanda.medicalClinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "PATIENTS")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "PATIENT_NAME", length = 50, nullable = false)
    private String firstName;
    @Column(name = "PATIENT_LAST_NAME", length = 50, nullable = false)
    private String lastName;
    @Column(name = "PATIENT_EMAIL", length = 50, nullable = false, unique = true)
    private String email;
    @Column(name = "PATIENT_PASSWORD", length = 50, nullable = false)
    private String password;
    @Column(name="PATIENT_ID_CARD_NO", length=50, nullable=false)
    private String idCardNo;
    @Column(name="PATIENT_PHONE_NO", length=50, nullable=false)
    private String phoneNumber;
    @Column(name="PATIENT_BIRTHDAY", length=50, nullable=false)
    private LocalDate birthday;
}
