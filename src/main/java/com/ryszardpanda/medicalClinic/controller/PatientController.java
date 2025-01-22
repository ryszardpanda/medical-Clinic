package com.ryszardpanda.medicalClinic.controller;

import com.ryszardpanda.medicalClinic.mapper.PatientMapper;
import com.ryszardpanda.medicalClinic.model.ChangePasswordDTO;
import com.ryszardpanda.medicalClinic.model.Patient;
import com.ryszardpanda.medicalClinic.model.PatientDTO;
import com.ryszardpanda.medicalClinic.model.PatientEditDTO;
import com.ryszardpanda.medicalClinic.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController // mowie ze to jest Bean Springowy oraz kontroler -> czyli tutaj bede przechwytywal zapytania
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final PatientMapper patientMapper;


    //PAGINATION
    @GetMapping("/patients")
    public Page<PatientDTO> getPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Patient> patientsPage = patientService.getPatients(pageRequest);
        return patientsPage.map(patientMapper::patientToDTO);
    }


    @PostMapping("/patients")
    private PatientDTO addPatient(@Valid @RequestBody PatientEditDTO patientEditDTO) {
        return patientMapper.patientToDTO(patientService.addPatient(patientEditDTO));
    }

    @GetMapping("/patients/{email}")
    public PatientDTO getPatientByEmail(@PathVariable String email) {
        return patientMapper.patientToDTO(patientService.getPatientByEmail(email));
    }

    // Metoda DELETE, która usunie pacjenta na podstawie adresu e-mail
    @DeleteMapping("/patients/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatientByEmail(@PathVariable String email) {
        patientService.deletePatientByEmail(email);
    }

    // Metoda PUT, która edytuje pacjenta na podstawie adresu e-mail
    @PutMapping("/patients/{email}")
    public PatientDTO updatePatient(@PathVariable String email, @Valid @RequestBody PatientEditDTO updatedPatientEditDTO) {
        return patientMapper.patientToDTO(patientService.updatePatient(email, updatedPatientEditDTO));
    }

    @PatchMapping("/patients/{email}")
    public void updatePassword(@PathVariable String email, @RequestBody ChangePasswordDTO updatedPassword) {
        patientMapper.patientToChangePasswordDTO(patientService.updatePassword(email, updatedPassword));
    }
}
