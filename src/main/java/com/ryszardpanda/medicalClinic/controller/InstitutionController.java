package com.ryszardpanda.medicalClinic.controller;

import com.ryszardpanda.medicalClinic.mapper.InstitutionMapper;
import com.ryszardpanda.medicalClinic.model.InstitutionDTO;
import com.ryszardpanda.medicalClinic.service.InstitutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class InstitutionController {
    private final InstitutionService institutionService;
    private final InstitutionMapper institutionMapper;

    @PostMapping("/institution")
    public InstitutionDTO addInstitution(@Valid @RequestBody InstitutionDTO institutionDTO){
        return institutionMapper.institutionToInstitutionDTO(institutionService.addInstitution(institutionDTO));
    }

    @PatchMapping("/{institutionId}/assign-doctor/{doctorId}")
    public InstitutionDTO assignDoctorToInstitution(@PathVariable Long institutionId, @PathVariable Long doctorId) {
        return institutionMapper.institutionToInstitutionDTO(institutionService.assignDoctorToInstitution(doctorId, institutionId));
    }

}
