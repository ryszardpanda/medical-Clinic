package com.ryszardpanda.medicalClinic.controller;

import com.ryszardpanda.medicalClinic.mapper.DoctorMapper;
import com.ryszardpanda.medicalClinic.model.*;
import com.ryszardpanda.medicalClinic.service.DoctorService;
import com.ryszardpanda.medicalClinic.service.InstitutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @GetMapping("/doctors")
    public List<DoctorDTO> getDoctors() {
        return doctorService.getDoctors().stream()
                .map(doctorMapper::doctorToDoctorDTO)
                .toList();
    }

    @PostMapping("/doctors")
    public DoctorDTO addDoctor(@Valid @RequestBody DoctorEditDTO doctorEditDTO) {
        return doctorMapper.doctorToDoctorDTO(doctorService.addDoctor(doctorEditDTO));
    }

    @GetMapping("/doctors/{email}")
    public DoctorDTO getDoctorByEmail(@PathVariable String email) {
        return doctorMapper.doctorToDoctorDTO(doctorService.getDoctorByEmail(email));
    }

    @DeleteMapping("/doctors/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctorByEmail(@PathVariable String email) {
        doctorService.deleteDoctorByEmail(email);
    }

    @PutMapping("/doctors/{email}")
    public DoctorDTO updateDoctor(@PathVariable String email, @Valid @RequestBody DoctorEditDTO updatedDoctorEditDTO) {
        return doctorMapper.doctorToDoctorDTO(doctorService.updateDoctor(email, updatedDoctorEditDTO));
    }

    @PatchMapping("/doctors/{email}")
    public void updatePassword(@PathVariable String email, @RequestBody ChangePasswordDTO updatedPassword) {
        doctorMapper.doctorToChangePasswordDTO(doctorService.updatePassword(email, updatedPassword));
    }

    @PatchMapping("/doctors/{doctorId}/assignToInstitution/{institutionId}")
    public DoctorDTO assignDoctorToInstitution(@PathVariable Long doctorId, @PathVariable Long institutionId) {
        return doctorMapper.doctorToDoctorDTO(doctorService.assignDoctorToInstitution(doctorId, institutionId));
    }
}
