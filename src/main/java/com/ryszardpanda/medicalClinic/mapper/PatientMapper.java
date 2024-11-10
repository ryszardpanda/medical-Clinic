package com.ryszardpanda.medicalClinic.mapper;

import com.ryszardpanda.medicalClinic.model.Patient;
import com.ryszardpanda.medicalClinic.model.PatientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")//bez tego nie tworzył się bean springowy
public interface PatientMapper {

    @Mapping(target = "fullName", source = "patient", qualifiedByName = "generateFullName")
    PatientDTO patientToDTO(Patient patient);

    @Named("generateFullName")
    default String generateFullName(Patient patient) {
        return patient.getFirstName() + " " + patient.getLastName();
    }

    Patient dtoToPatient(PatientDTO patientDTO);
}