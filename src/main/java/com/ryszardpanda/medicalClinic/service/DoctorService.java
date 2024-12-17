package com.ryszardpanda.medicalClinic.service;

import com.ryszardpanda.medicalClinic.exceptions.EmailAlreadyInUse;
import com.ryszardpanda.medicalClinic.exceptions.NoIdNumberException;
import com.ryszardpanda.medicalClinic.exceptions.PersonNotFoundException;
import com.ryszardpanda.medicalClinic.mapper.DoctorMapper;
import com.ryszardpanda.medicalClinic.model.ChangePasswordDTO;
import com.ryszardpanda.medicalClinic.model.Doctor;
import com.ryszardpanda.medicalClinic.model.DoctorEditDTO;
import com.ryszardpanda.medicalClinic.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }
    @Transactional
    public Doctor addDoctor(DoctorEditDTO doctorEditDTO) {
        Doctor doctor = doctorMapper.doctorEditDTOToDoctor(doctorEditDTO);
        validateDoctorFields(doctor);
        Optional<Doctor> existingDoctor = doctorRepository.findByEmail(doctorEditDTO.getEmail());
        if (existingDoctor.isPresent()) {
            throw new EmailAlreadyInUse("Doktor o takim emailu już istnieje", HttpStatus.CONFLICT);
        }
        return doctorRepository.save(doctor);
    }

    public Doctor getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email).orElseThrow(() -> new PersonNotFoundException("Nie znaleziono Doktora o takim emailu.",
                HttpStatus.NOT_FOUND));
    }
    @Transactional
    public void deleteDoctorByEmail(String email) {
        Doctor doctor = doctorRepository.findByEmail(email).orElseThrow(() -> new PersonNotFoundException("Nie znaleziono Doktora o takim emailu.",
                HttpStatus.NOT_FOUND));
        doctorRepository.delete(doctor);
    }

    @Transactional
    public Doctor updateDoctor(String email, DoctorEditDTO doctorEditDTO) {
        Doctor updatedDoctor = doctorMapper.doctorEditDTOToDoctor(doctorEditDTO);
        Doctor doctor = doctorRepository.findByEmail(email).orElseThrow(() -> new PersonNotFoundException("Nie znaleziono użytkownika o takim ID",
                HttpStatus.NOT_FOUND));
        doctor.setFirstName(updatedDoctor.getFirstName());
        doctor.setLastName(updatedDoctor.getLastName());
        doctor.setEmail(updatedDoctor.getEmail());
        doctor.setSpecialization(updatedDoctor.getSpecialization());

        return doctor;
    }
    @Transactional
    public Doctor updatePassword(String email, ChangePasswordDTO updatedPassword) {
        Doctor doctor = doctorRepository.findByEmail(email).orElseThrow(() -> new PersonNotFoundException("Nie znaleziono użytkownika o takim ID",
                HttpStatus.NOT_FOUND));
        doctor.setPassword(updatedPassword.getPassword());
        return doctor;
    }

    public Doctor findDoctorById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() -> new NoIdNumberException("Nie znaleziono Doktora o takim ID",
                HttpStatus.NOT_FOUND));
    }

    public void validateDoctorFields(Doctor doctor) {
        if (doctor.getFirstName() == null || doctor.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("Imie nie może być puste, lub być nullem");
        }
        if (doctor.getLastName() == null || doctor.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Nazwisko nie może być nullem lub być puste");
        }
        if (doctor.getEmail() == null || doctor.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email nie może być pusty, lub być nullem");
        }
        if (doctor.getPassword() == null || doctor.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Hasło nie może być nullem lub być puste");
        }
        if (doctor.getSpecialization() == null || doctor.getSpecialization().isEmpty()) {
            throw new IllegalArgumentException("Specjalizacja nie może być nullem lub być pusta");
        }
    }
}