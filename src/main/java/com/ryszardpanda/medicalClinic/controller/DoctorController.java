package com.ryszardpanda.medicalClinic.controller;

import com.ryszardpanda.medicalClinic.mapper.DoctorMapper;
import com.ryszardpanda.medicalClinic.model.Doctor;
import com.ryszardpanda.medicalClinic.model.DoctorDTO;
import com.ryszardpanda.medicalClinic.model.DoctorEditDTO;
import com.ryszardpanda.medicalClinic.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @GetMapping("/doctors")
    public List<DoctorDTO> getDoctors(){
        return doctorService.getDoctors().stream()
                .map(doctorMapper::doctorToDoctorDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/doctors")
    public DoctorEditDTO addDoctor(@Valid @RequestBody DoctorEditDTO doctorEditDTO){
       return doctorMapper.doctorToDoctorEditDTO(doctorService.addDoctor(doctorEditDTO));
    }

    @GetMapping("/doctors/{email}")
    public DoctorDTO getDoctorByEmail(@PathVariable String email){
        return doctorMapper.doctorToDoctorDTO(doctorService.getDoctorByEmail(email));
    }

    @DeleteMapping("/doctors/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctorByEmail(@PathVariable String email) {
        boolean deleted = doctorService.deleteDoctorByEmail(email);
    }

    @PutMapping("/doctors/{email}")
    public DoctorEditDTO updateDoctor(@PathVariable String email, @Valid @RequestBody DoctorEditDTO updatedDoctorEditDTO){
        return doctorMapper.doctorToDoctorEditDTO(doctorService.updateDoctor(email, updatedDoctorEditDTO));
    }

    @PatchMapping("/doctors/{email}")
    public DoctorDTO updatePassword(@PathVariable String email, @RequestBody Doctor updatedPassword){
        return doctorMapper.doctorToDoctorDTO(doctorService.updatePassword(email, updatedPassword));
    }
}
