package com.ryszardpanda.medicalClinic.mapper;

import com.ryszardpanda.medicalClinic.model.Patient;
import com.ryszardpanda.medicalClinic.model.PatientDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") //bez tego nie tworzył się bean springowy
public interface PatientMapper { //poniewaz pola mam takie same w Patient oraz PatientDTO nie musze nic wiecej robic
    PatientDTO patientToDTO(Patient patient);
}
