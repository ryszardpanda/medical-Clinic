package com.ryszardpanda.medicalClinic.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "PATIENTS")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Patient))
            return false;

        Patient other = (Patient) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
