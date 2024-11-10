package com.ryszardpanda.medicalClinic.controller;

import com.ryszardpanda.medicalClinic.mapper.PatientMapper;
import com.ryszardpanda.medicalClinic.model.PatientDTO;
import com.ryszardpanda.medicalClinic.service.PatientService;
import com.ryszardpanda.medicalClinic.model.Patient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // mowie ze to jest Bean Springowy oraz kontroler -> czyli tutaj bede przechwytywal zapytania
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final PatientMapper patientMapper;

    // to jest po prostu definicja endpointu na sciezce "/patients"
    // GET /patients
    @GetMapping("/patients")
    public List<PatientDTO> getPatients() {
        return patientService.getPatients().stream()
                .map(patientMapper::patientToDTO)
                .toList();
    }

    @PostMapping("/patients")
    private PatientDTO addPatient(@Valid @RequestBody PatientDTO patientDTO) {
        Patient patient = patientMapper.dtoToPatient(patientDTO);
        return patientMapper.patientToDTO(patientService.addPatient(patient));
    }

    @GetMapping("/patients/{email}")
    public PatientDTO getPatientByEmail(@PathVariable String email) {
        return patientMapper.patientToDTO(patientService.getPatientByEmail(email));
    }

    // Metoda DELETE, która usunie pacjenta na podstawie adresu e-mail
    @DeleteMapping("/patients/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatientByEmail(@PathVariable String email) {
        boolean deleted = patientService.deletePatientByEmail(email);
    }

    // Metoda PUT, która edytuje pacjenta na podstawie adresu e-mail
    @PutMapping("/patients/{email}")
    public PatientDTO updatePatient(@PathVariable String email, @Valid @RequestBody PatientDTO updatedPatientDTO) {
        Patient updatedPatient = patientMapper.dtoToPatient(updatedPatientDTO);
        return patientMapper.patientToDTO(patientService.updatePatient(email, updatedPatient));
    }

    @PatchMapping("/patients/{email}")
    public PatientDTO updatePassword(@PathVariable String email, @RequestBody Patient updatedPassword) {
        return patientMapper.patientToDTO(patientService.updatePassword(email, updatedPassword));
    }
}
