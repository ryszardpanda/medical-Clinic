package com.ryszardpanda.medicalClinic.controller;

import com.ryszardpanda.medicalClinic.mapper.InstitutionMapper;
import com.ryszardpanda.medicalClinic.model.InstitutionDTO;
import com.ryszardpanda.medicalClinic.service.InstitutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institution")
public class InstitutionController {
    private final InstitutionService institutionService;
    private final InstitutionMapper institutionMapper;

    @Operation(summary = "Dodaj instytucję")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Instytucja dodana",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = InstitutionDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @PostMapping()
    public InstitutionDTO addInstitution(@Valid @RequestBody InstitutionDTO institutionDTO){
        return institutionMapper.institutionToInstitutionDTO(institutionService.addInstitution(institutionDTO));
    }

    @Operation(summary = "Przypisz doktora do instytucji")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doktor przypisany do instytucji",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = InstitutionDTO.class)) }),
            @ApiResponse(responseCode = "409", description = "Conflict - wizyta nakłada się na już zarezerwowaną",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @PatchMapping("/{institutionId}/assign-doctor/{doctorId}")
    public InstitutionDTO assignDoctorToInstitution(@PathVariable Long institutionId, @PathVariable Long doctorId) {
        return institutionMapper.institutionToInstitutionDTO(institutionService.assignDoctorToInstitution(doctorId, institutionId));
    }
}
