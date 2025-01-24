package com.ryszardpanda.medicalClinic.controller;

import com.ryszardpanda.medicalClinic.mapper.PatientMapper;
import com.ryszardpanda.medicalClinic.model.*;
import com.ryszardpanda.medicalClinic.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @Operation(summary = "Znajdż pacjentów")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pacjenci zwróceni",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @GetMapping("/patients")
    public Page<PatientDTO> getPatients(@ParameterObject Pageable pageable) {
        Page<Patient> patientsPage = patientService.getPatients(pageable);
        return patientsPage.map(patientMapper::patientToDTO);
    }

    @Operation(summary = "Dodaj pacjenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pacjent dodany",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class)) }),
            @ApiResponse(responseCode = "409", description = "Conflict - pacjent z takim email już istnieje",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @PostMapping("/patients")
    private PatientDTO addPatient(@Valid @RequestBody PatientEditDTO patientEditDTO) {
        return patientMapper.patientToDTO(patientService.addPatient(patientEditDTO));
    }

    @Operation(summary = "Znajdź pacjenta po emailu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pacjent znaleziony",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @GetMapping("/patients/{email}")
    public PatientDTO getPatientByEmail(@PathVariable String email) {
        return patientMapper.patientToDTO(patientService.getPatientByEmail(email));
    }

    @Operation(summary = "Usuń pacjenta po emailu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pacjent usunięty",
                    content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    // Metoda DELETE, która usunie pacjenta na podstawie adresu e-mail
    @DeleteMapping("/patients/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatientByEmail(@PathVariable String email) {
        patientService.deletePatientByEmail(email);
    }

    @Operation(summary = "Update pacjenta po emailu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pacjent zupdatowany",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    // Metoda PUT, która edytuje pacjenta na podstawie adresu e-mail
    @PutMapping("/patients/{email}")
    public PatientDTO updatePatient(@PathVariable String email, @Valid @RequestBody PatientEditDTO updatedPatientEditDTO) {
        return patientMapper.patientToDTO(patientService.updatePatient(email, updatedPatientEditDTO));
    }

    @Operation(summary = "Update hasła pacjenta po emialu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hasło pacjenta zmienione",
                    content = {@Content }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @PatchMapping("/patients/{email}")
    public void updatePassword(@PathVariable String email, @RequestBody ChangePasswordDTO updatedPassword) {
        patientMapper.patientToChangePasswordDTO(patientService.updatePassword(email, updatedPassword));
    }
}
