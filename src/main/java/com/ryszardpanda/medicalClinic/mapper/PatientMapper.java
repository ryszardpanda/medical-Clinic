package com.ryszardpanda.medicalClinic.mapper;

import com.ryszardpanda.medicalClinic.model.Patient;
import com.ryszardpanda.medicalClinic.model.PatientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")//bez tego nie tworzył się bean springowy
public interface PatientMapper {
    @Mapping(target = "fullName", expression = "java(patient.getFirstName() + \" \" + patient.getLastName())")
    PatientDTO patientToDTO(Patient patient);

    //drugi sposob
//    default String generateFullName(Patient patient) {
//        return patient.getFirstName() + " " + patient.getLastName();
//    }

}
