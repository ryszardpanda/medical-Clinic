package com.ryszardpanda.medicalClinic.controller;

import com.ryszardpanda.medicalClinic.mapper.DoctorMapper;
import com.ryszardpanda.medicalClinic.model.*;
import com.ryszardpanda.medicalClinic.service.DoctorService;
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
public class DoctorController {
    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @Operation(summary = "Zwróc doktorów")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doktorzy poprawnie zwróceni",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = DoctorDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
            content = {@Content})
    })
    @GetMapping("/doctors")
    public Page<DoctorDTO> getDoctors(@ParameterObject Pageable pageable) {
        Page<Doctor> doctors = doctorService.getDoctors(pageable);
        return doctors.map(doctorMapper::doctorToDoctorDTO);
    }

    @Operation(summary = "Dodaj doktora")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doktor dodany",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDTO.class)) }),
            @ApiResponse(responseCode = "409", description = "Konflikt - doktor o takim emailu już istnieje",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @PostMapping("/doctors")
    public DoctorDTO addDoctor(@Valid @RequestBody DoctorEditDTO doctorEditDTO) {
        return doctorMapper.doctorToDoctorDTO(doctorService.addDoctor(doctorEditDTO));
    }

    @Operation(summary = "Znajdż doktora po emailu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doktor znaleziony",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @GetMapping("/doctors/{email}")
    public DoctorDTO getDoctorByEmail(@PathVariable String email) {
        return doctorMapper.doctorToDoctorDTO(doctorService.getDoctorByEmail(email));
    }

    @Operation(summary = "Usuń doktora po emailu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doktor usunięty",
                    content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @DeleteMapping("/doctors/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctorByEmail(@PathVariable String email) {
        doctorService.deleteDoctorByEmail(email);
    }

    @Operation(summary = "Update doktora po emailu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doktor zupdatowany",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @PutMapping("/doctors/{email}")
    public DoctorDTO updateDoctor(@PathVariable String email, @Valid @RequestBody DoctorEditDTO updatedDoctorEditDTO) {
        return doctorMapper.doctorToDoctorDTO(doctorService.updateDoctor(email, updatedDoctorEditDTO));
    }

    @Operation(summary = "Update hasła doktora mo emailu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hasło doktra zupdatowane",
                    content = {@Content }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content})
    })
    @PatchMapping("/doctors/{email}")
    public void updatePassword(@PathVariable String email, @RequestBody ChangePasswordDTO updatedPassword) {
        doctorMapper.doctorToChangePasswordDTO(doctorService.updatePassword(email, updatedPassword));
    }
}