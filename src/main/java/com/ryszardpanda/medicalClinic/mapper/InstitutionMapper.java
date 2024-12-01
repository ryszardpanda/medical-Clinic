package com.ryszardpanda.medicalClinic.mapper;

import com.ryszardpanda.medicalClinic.model.Institution;
import com.ryszardpanda.medicalClinic.model.InstitutionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {
    Institution institutionDTOtoInstitution(InstitutionDTO institutionDTO);
    InstitutionDTO institutionToInstitutionDTO(Institution institution);
}
