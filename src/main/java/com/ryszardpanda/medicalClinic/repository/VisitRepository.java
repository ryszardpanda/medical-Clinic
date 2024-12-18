package com.ryszardpanda.medicalClinic.repository;

import com.ryszardpanda.medicalClinic.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByPatientId(Long patientId);

    List<Visit> findByInstitutionId(Long institutionId);

    List<Visit> findByStartDate(LocalDateTime localDateTime);

    @Query(value = "SELECT * FROM visit v WHERE v.startDate > CURRENT_TIMESTAMP AND v.patient_id IS NULL", nativeQuery = true)
    List<Visit> findAvailableVisits();

    @Query("SELECT v " +
            "FROM Visit v " +
            "WHERE v.doctor.id = :doctorId " +
            "AND v.startDate < :endDate " +
            "AND v.endDate > :startDate")
    List<Visit> findOverlappingVisits(@Param("doctorId") Long doctorId,
                                      @Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate);

}
