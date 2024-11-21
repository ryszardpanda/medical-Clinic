package com.ryszardpanda.medicalClinic.repository;

import com.ryszardpanda.medicalClinic.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PatientDAORepository extends JpaRepository<Patient, Long> {

    // Znajdź pacjenta po emailu
    Optional<Patient> findByEmail(String email);

    // Aktualizacja hasła pacjenta
    @Modifying
    @Query("UPDATE Patient p SET p.password = :password WHERE p.email = :email")
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);
}
