package com.ryszardpanda.medicalClinic.mapper;

import com.ryszardpanda.medicalClinic.model.Visit;
import com.ryszardpanda.medicalClinic.model.VisitDTO;
import com.ryszardpanda.medicalClinic.model.VisitEditDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitMapper {
    Visit visitDTOToVisit(VisitDTO visitDTO);

    Visit visitEditDTOToVisit(VisitEditDTO visitEditDTO);

    VisitEditDTO visitToVisitEditDTO(Visit visit);

    default VisitDTO visitToVisitDTO(Visit visit) {
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setId(visit.getId());
        visitDTO.setStartDate(visit.getStartDate());
        visitDTO.setEndDate(visit.getEndDate());
        visitDTO.setDoctorId(visit.getDoctor().getId());
        visitDTO.setInstitutionId(visit.getInstitution().getId());
        return visitDTO;
    }
}
