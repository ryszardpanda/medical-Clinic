package com.ryszardpanda.medicalClinic.mapper;

import com.ryszardpanda.medicalClinic.model.Doctor;
import com.ryszardpanda.medicalClinic.model.DoctorDTO;
import com.ryszardpanda.medicalClinic.model.DoctorEditDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    Doctor doctorEditDTOToDoctor (DoctorEditDTO doctorEditDTO);
    DoctorEditDTO doctorToDoctorEditDTO (Doctor doctor);
    Doctor doctorDtoToDoctor(DoctorDTO doctorDTO);
    DoctorDTO doctorToDoctorDTO (Doctor doctor);
}
