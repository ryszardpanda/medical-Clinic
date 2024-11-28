package com.ryszardpanda.medicalClinic.mapper;

import com.ryszardpanda.medicalClinic.model.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    Doctor doctorEditDTOToDoctor (DoctorEditDTO doctorEditDTO);
    DoctorEditDTO doctorToDoctorEditDTO (Doctor doctor);
    Doctor doctorDtoToDoctor(DoctorDTO doctorDTO);
    DoctorDTO doctorToDoctorDTO (Doctor doctor);
    ChangePasswordDTO doctorToChangePasswordDTO(Doctor doctor);
}
