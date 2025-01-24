package com.ryszardpanda.medicalClinic.controller;

import com.ryszardpanda.medicalClinic.mapper.DoctorMapper;
import com.ryszardpanda.medicalClinic.model.*;
import com.ryszardpanda.medicalClinic.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @GetMapping("/doctors")
    public Page<DoctorDTO> getDoctors(Pageable pageable) {
        Page<Doctor> doctors = doctorService.getDoctors(pageable);
        return doctors.map(doctorMapper::doctorToDoctorDTO);
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
}