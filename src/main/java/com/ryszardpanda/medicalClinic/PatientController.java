package com.ryszardpanda.medicalClinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public List<Patient> addPatients(@RequestBody List<Patient> patients) {
        return patientService.addPatients(patients);
    }

    @GetMapping("/patients/{email}")
    public Patient getPatientByEmail(@PathVariable String email) {
        return patientService.getPatientByEmail(email);
    }

    // Metoda DELETE, która usunie pacjenta na podstawie adresu e-mail
    //nie ogarnalbym
    @DeleteMapping("/patients/{email}")
    public ResponseEntity<Void> deletePatientByEmail(@PathVariable String email) {
        boolean deleted = patientService.deletePatientByEmail(email);
        if (deleted) {
            return ResponseEntity.noContent().build(); // Zwraca 204 No Content, jeśli pacjent został usunięty
        } else {
            return ResponseEntity.notFound().build(); // Zwraca 404 Not Found, jeśli pacjent nie istnieje
        }
    }

    // Metoda PUT, która edytuje pacjenta na podstawie adresu e-mail
    @PutMapping("/patients/{email}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String email, @RequestBody Patient updatedPatient) {
        Patient patient = patientService.updatePatient(email, updatedPatient);
        if (patient != null) {
            return ResponseEntity.ok(patient); // Zwraca 200 OK z edytowanym pacjentem
        } else {
            return ResponseEntity.notFound().build(); // Zwraca 404 Not Found, jeśli pacjent nie został znaleziony
        }
    }


}
