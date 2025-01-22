package com.ryszardpanda.medicalClinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryszardpanda.medicalClinic.model.*;
import com.ryszardpanda.medicalClinic.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import com.ryszardpanda.medicalClinic.model.Doctor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DoctorService doctorService;

    @Test
    void getDoctors_DoctorsListExist_DoctorsReturned() throws Exception {
        Doctor doctor = new Doctor(1L, "Adam", "Adamslki", "elo@.pl", "123", "it", new HashSet<>());
        Doctor doctor1 = new Doctor(1L, "Adam", "Adamslki", "elo@.pl", "123", "it", new HashSet<>());
        Doctor doctor2 = new Doctor(1L, "Adam", "Adamslki", "elo@.pl", "123", "it", new HashSet<>());

        List<Doctor> doctorsList = List.of(doctor, doctor1, doctor2);
        PageImpl<Doctor> doctorsPage = new PageImpl<>(doctorsList);

        Mockito.when(doctorService.getDoctors(PageRequest.of(0, 2))).thenReturn(doctorsPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctors"))
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].firstName").value("Adam"))
                .andExpect(jsonPath("$.content[0].lastName").value("Adamslki"))
                .andExpect(jsonPath("$.content[0].email").value("elo@.pl"))
                .andExpect(jsonPath("$.content[0].specialization").value("it"))
                .andExpect(jsonPath("$.content[1].id").value(1L))
                .andExpect(jsonPath("$.content[1].firstName").value("Adam"))
                .andExpect(jsonPath("$.content[1].lastName").value("Adamslki"))
                .andExpect(jsonPath("$.content[1].email").value("elo@.pl"))
                .andExpect(jsonPath("$.content[1].specialization").value("it"))
                .andExpect(jsonPath("$.content[2].id").value(1L))
                .andExpect(jsonPath("$.content[2].firstName").value("Adam"))
                .andExpect(jsonPath("$.content[2].lastName").value("Adamslki"))
                .andExpect(jsonPath("$.content[2].email").value("elo@.pl"))
                .andExpect(jsonPath("$.content[2].specialization").value("it"))
                .andExpect(jsonPath("$.totalElements").value(3));
    }

    @Test
    void addDoctor_PayloadCorrect_DoctorAdded() throws Exception {
        Doctor doctor = new Doctor(1L, "Adam", "Adamslki", "elo@op.pl", "123", "it", new HashSet<>());
        DoctorEditDTO doctorEditDTO = new DoctorEditDTO(1L, "Adam", "Adamslki", "elo@op.pl", "123", "it");

        Mockito.when(doctorService.addDoctor(any())).thenReturn(doctor);

        mockMvc.perform(post("/doctors")
                        .content(objectMapper.writeValueAsString(doctorEditDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Adam"))
                .andExpect(jsonPath("$.lastName").value("Adamslki"))
                .andExpect(jsonPath("$.email").value("elo@op.pl"))
                .andExpect(jsonPath("$.specialization").value("it"));
    }

    @Test
    void getDoctorsByEmail_DoctorExist_DoctorReturned() throws Exception {
        Doctor doctor = new Doctor(1L, "Adam", "Adamslki", "elo@op.pl", "123", "it", new HashSet<>());

        Mockito.when(doctorService.getDoctorByEmail(any())).thenReturn(doctor);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/elo@op.pl"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Adam"))
                .andExpect(jsonPath("$.lastName").value("Adamslki"))
                .andExpect(jsonPath("$.email").value("elo@op.pl"))
                .andExpect(jsonPath("$.specialization").value("it"));
    }

    @Test
    void deleteDoctor_DoctorExist_DoctorDeleted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/doctors/elo@op.pl"))
                .andExpect(status().isNoContent());

        Mockito.verify(doctorService, Mockito.times(1)).deleteDoctorByEmail("elo@op.pl");
    }

    @Test
    void updateDoctor_DoctorExist_DoctorUpdated() throws Exception {
        Doctor doctor = new Doctor(1L, "Adam", "Adamslki", "elo@op.pl", "123", "it", new HashSet<>());
        DoctorEditDTO doctorEditDTO = new DoctorEditDTO(1L, "Adam", "Adamslki", "elo@op.pl", "123", "it");

        Mockito.when(doctorService.updateDoctor(doctor.getEmail(), doctorEditDTO)).thenReturn(doctor);

        mockMvc.perform(put("/doctors/elo@op.pl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorEditDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Adam"))
                .andExpect(jsonPath("$.lastName").value("Adamslki"))
                .andExpect(jsonPath("$.email").value("elo@op.pl"))
                .andExpect(jsonPath("$.specialization").value("it"));
    }

    @Test
    void updatePassword_DoctorExist_PasswordUpdated() throws Exception {
        Doctor doctor = new Doctor(1L, "Adam", "Adamslki", "elo@op.pl", "123", "it", new HashSet<>());
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("eloelo");
        Doctor updatedDoctor = new Doctor(1L, "Adam", "Adamslki", "elo@op.pl", "eloelo", "it", new HashSet<>());

        Mockito.when(doctorService.updatePassword(doctor.getPassword(), changePasswordDTO)).thenReturn(updatedDoctor);

        mockMvc.perform(patch("/doctors/elo@op.pl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordDTO)))
                .andExpect(status().isOk());
    }
}
