package com.ryszardpanda.medicalClinic.repository;

import com.ryszardpanda.medicalClinic.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}
