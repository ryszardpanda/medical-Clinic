package com.ryszardpanda.medicalClinic.repository;

import com.ryszardpanda.medicalClinic.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByPatientId(Long patientId);

    List<Visit> findByDate(LocalDateTime localDateTime);

    @Query(value = "SELECT * FROM visit v WHERE v.date_of_visit > CURRENT_TIMESTAMP AND v.patient_id IS NULL", nativeQuery = true)
    List<Visit> findAvailableVisits();
}
