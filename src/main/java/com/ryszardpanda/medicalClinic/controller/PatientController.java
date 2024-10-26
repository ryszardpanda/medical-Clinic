package com.ryszardpanda.medicalClinic.controller;

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
    @GetMapping("/patients")
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @PostMapping("/patients")
    public Patient addPatients(@RequestBody Patient patients) {
        return patientService.addPatient(patients);
    }

    @GetMapping("/patients/{email}")
    public Patient getPatientByEmail(@PathVariable String email) {
        return patientService.getPatientByEmail(email);
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
    public Patient updatePatient(@PathVariable String email, @RequestBody Patient updatedPatient) {
        return patientService.updatePatient(email, updatedPatient);
    }

    @PatchMapping("/patients/{email}")
    public Patient updatePassword(@PathVariable String email, @RequestBody Patient updatedPassword) {
        return patientService.updatePassword(email, updatedPassword);
    }
}
