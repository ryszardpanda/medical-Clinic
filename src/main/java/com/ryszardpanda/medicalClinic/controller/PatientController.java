package com.ryszardpanda.medicalClinic.controller;

import com.ryszardpanda.medicalClinic.mapper.PatientMapper;
import com.ryszardpanda.medicalClinic.model.PatientDTO;
import com.ryszardpanda.medicalClinic.service.PatientService;
import com.ryszardpanda.medicalClinic.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // mowie ze to jest Bean Springowy oraz kontroler -> czyli tutaj bede przechwytywal zapytania
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    // to jest po prostu definicja endpointu na sciezce "/patients"
    // GET /patients
    @GetMapping("/patients") //patientDTO trzeba w service takto tez zamienic?
    public List<PatientDTO> getPatients() {
        return patientService.getPatients().stream()
                .map(PatientMapper::patientToDTO)
                .toList();
    }

    @PostMapping("/patients")
    private PatientDTO addPatient(@RequestBody Patient patient) {
        return PatientMapper.patientToDTO(patientService.addPatient(patient));
    }

    @GetMapping("/patients/{email}")
    public PatientDTO getPatientByEmail(@PathVariable String email) {
        return PatientMapper.patientToDTO(patientService.getPatientByEmail(email));
    }

    // Metoda DELETE, która usunie pacjenta na podstawie adresu e-mail
    //nie ogarnalbym
    @DeleteMapping("/patients/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatientByEmail(@PathVariable String email) {
        boolean deleted = patientService.deletePatientByEmail(email);
    }

    // Metoda PUT, która edytuje pacjenta na podstawie adresu e-mail
    @PutMapping("/patients/{email}")
    public PatientDTO updatePatient(@PathVariable String email, @RequestBody Patient updatedPatient) {
        return PatientMapper.patientToDTO(patientService.updatePatient(email, updatedPatient));
    }

    @PatchMapping("/patients/{email}")
    public PatientDTO updatePassword(@PathVariable String email, @RequestBody Patient updatedPassword) {
        return PatientMapper.patientToDTO(patientService.updatePassword(email, updatedPassword));
    }
}
