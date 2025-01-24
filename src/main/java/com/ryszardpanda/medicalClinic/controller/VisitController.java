package com.ryszardpanda.medicalClinic.controller;

import com.ryszardpanda.medicalClinic.mapper.VisitMapper;
import com.ryszardpanda.medicalClinic.model.DoctorDTO;
import com.ryszardpanda.medicalClinic.model.VisitDTO;
import com.ryszardpanda.medicalClinic.model.VisitEditDTO;
import com.ryszardpanda.medicalClinic.service.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;
    private final VisitMapper visitMapper;

    @Operation(summary = "Stwórz wizytę")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wizyta stworzona",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = VisitDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @PostMapping()
    public VisitDTO createVisit(@Valid @RequestBody VisitEditDTO visitEditDTO) {
        return visitMapper.visitToVisitDTO(visitService.createVisit(visitEditDTO));
    }

    @Operation(summary = "Zabookuj wizytę")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wizyta zabookowana",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = VisitDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @PatchMapping("/{visitId}/book")
    public VisitDTO bookVisit(@PathVariable Long visitId, @RequestParam Long patientId) {
        return visitMapper.visitToVisitDTO(visitService.bookVisit(visitId, patientId));
    }

    @Operation(summary = "Znajdź wizyty dla pacjenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wizyty znalezione",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = VisitDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @GetMapping("/patient/{patientId}")
    public List<VisitDTO> getPatientVisits(@PathVariable Long patientId) {
        return visitService.getPatientsVisits(patientId).stream()
                .map(visitMapper::visitToVisitDTO).toList();
    }

    @Operation(summary = "Sprawdź dostępne wizyty")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dostępne wizyty znalezione",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @GetMapping("/available")
    public List<VisitDTO> getAvailableVisits() {
        return visitService.getAvailableVisit().stream()
                .map(visitMapper::visitToVisitDTO).toList();
    }
}