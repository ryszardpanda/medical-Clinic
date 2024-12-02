package com.ryszardpanda.medicalClinic.mapper;

import com.ryszardpanda.medicalClinic.model.Visit;
import com.ryszardpanda.medicalClinic.model.VisitDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitMapper {
    Visit visitDTOToVisit(VisitDTO visitDTO);
    VisitDTO visitToVisitDTO(Visit visit);
}
