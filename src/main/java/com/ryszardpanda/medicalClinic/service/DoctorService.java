package com.ryszardpanda.medicalClinic.service;

import com.ryszardpanda.medicalClinic.exceptions.EmailAlreadyInUse;
import com.ryszardpanda.medicalClinic.exceptions.PersonNotFoundException;
import com.ryszardpanda.medicalClinic.mapper.DoctorMapper;
import com.ryszardpanda.medicalClinic.model.Doctor;
import com.ryszardpanda.medicalClinic.model.DoctorEditDTO;
import com.ryszardpanda.medicalClinic.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public List<Doctor> getDoctors() {
        return doctorRepository.getDoctors();
    }

    public Doctor addDoctor(DoctorEditDTO doctorEditDTO) {
        Doctor doctor = doctorMapper.doctorEditDTOToDoctor(doctorEditDTO);
        validateDoctorFields(doctor);
        doctorRepository.getDoctorByEmail(doctor.getEmail())
                .ifPresent(existingDoctor -> {
                    throw new EmailAlreadyInUse("Doktor o takim emailu już istnieje", HttpStatus.CONFLICT);
                });
        return doctorRepository.addDoctor(doctor);
    }

    public Doctor getDoctorByEmail(String email) {
        return doctorRepository.getDoctorByEmail(email).orElseThrow(() -> new PersonNotFoundException("Nie znaleziono Doktora o takim emailu.",
                HttpStatus.NOT_FOUND));
    }

    public void deleteDoctorByEmail(String email) {
        if (!doctorRepository.deleteDoctorByEmail(email)) {
            throw new PersonNotFoundException("Nie znaleziono pacjenta o takim ID", HttpStatus.NOT_FOUND);
        }
    }

    public Doctor updateDoctor(String email, DoctorEditDTO doctorEditDTO) {
        Doctor updatedDoctor = doctorMapper.doctorEditDTOToDoctor(doctorEditDTO);
        return doctorRepository.updateDoctor(email, updatedDoctor).orElseThrow(() -> new PersonNotFoundException("Nie znaleziono użytkownika o takim ID",
                HttpStatus.NOT_FOUND));
    }

    public Doctor updatePassword(String email, Doctor updatedPassword) {
        return doctorRepository.updatePassword(email, updatedPassword).orElseThrow(() -> new PersonNotFoundException("Nie znaleziono użytkownika o takim ID",
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
