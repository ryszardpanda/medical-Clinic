package com.ryszardpanda.medicalClinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryszardpanda.medicalClinic.model.Doctor;
import com.ryszardpanda.medicalClinic.model.Institution;
import com.ryszardpanda.medicalClinic.model.InstitutionDTO;
import com.ryszardpanda.medicalClinic.service.InstitutionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InstitutionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InstitutionService institutionService;

    @Test
    void addInstitution_PayloadCorrect_InstitutionAdded() throws Exception {
        Institution institution = new Institution(1L, "Inst1", new HashSet<>());
        InstitutionDTO institutionDTO = new InstitutionDTO(1L, "Inst1");

        Mockito.when(institutionService.addInstitution(any())).thenReturn(institution);

        mockMvc.perform(post("/institution")
                .content(objectMapper.writeValueAsString(institutionDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Inst1"));
    }

    @Test
    void assignDoctorToInstitution_DoctorAndInstExist_DoctorAssigned() throws Exception {
        Institution institution = new Institution(1L, "Inst1", new HashSet<>());
        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());
        Set<Doctor> doctors = Set.of(doctor);
        Institution institutionAssignedDoctor = new Institution(1L, "Inst1", doctors);

        Mockito.when(institutionService.assignDoctorToInstitution(doctor.getId(), institution.getId())).thenReturn(institutionAssignedDoctor);

        mockMvc.perform(patch("/institution/1/assign-doctor/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Inst1"));
    }
}
