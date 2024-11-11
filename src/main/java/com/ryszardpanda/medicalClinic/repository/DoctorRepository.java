package com.ryszardpanda.medicalClinic.repository;

import com.ryszardpanda.medicalClinic.model.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DoctorRepository {
    private final List<Doctor> doctors;

    public List<Doctor> getDoctors() {
        return new ArrayList<>(doctors);
    }

    public Doctor addDoctor(Doctor doctor) {
        doctors.add(doctor);
        return doctor;
    }

    public Optional<Doctor> getDoctorByEmail(String email) {
        return doctors.stream()
                .filter(doctor -> doctor.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public boolean deleteDoctorByEmail(String email) {
        return doctors.removeIf(doctor -> doctor.getEmail().equalsIgnoreCase(email));
    }

    public Optional<Doctor> updateDoctor(String email, Doctor updatedDoctor) {
        Optional<Doctor> optionalDoctor = getDoctorByEmail(email);
        optionalDoctor.ifPresent(doctor -> {
            doctor.setFirstName(updatedDoctor.getFirstName());
            doctor.setLastName(updatedDoctor.getLastName());
            doctor.setEmail(updatedDoctor.getEmail());
            doctor.setPassword(updatedDoctor.getPassword());
            doctor.setSpecialization(updatedDoctor.getSpecialization());
        });
        return optionalDoctor;
    }

    public Optional<Doctor> updatePassword(String email, Doctor updatedPassword){
        Optional<Doctor> optionalDoctor = getDoctorByEmail(email);
        optionalDoctor.ifPresent(doctor -> doctor.setPassword(updatedPassword.getPassword()));
        return optionalDoctor;
    }

}
