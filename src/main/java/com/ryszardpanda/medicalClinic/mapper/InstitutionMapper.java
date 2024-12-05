package com.ryszardpanda.medicalClinic.mapper;

import com.ryszardpanda.medicalClinic.model.Institution;
import com.ryszardpanda.medicalClinic.model.InstitutionDTO;
import com.ryszardpanda.medicalClinic.model.InstitutionEditDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {
    Institution institutionDTOtoInstitution(InstitutionDTO institutionDTO);
    InstitutionDTO institutionToInstitutionDTO(Institution institution);
    Institution institutionEditDTOToInstitution(InstitutionEditDTO institutionEditDTO);
    InstitutionEditDTO institutionToInstitutionEditDTO(Institution institution);
}
