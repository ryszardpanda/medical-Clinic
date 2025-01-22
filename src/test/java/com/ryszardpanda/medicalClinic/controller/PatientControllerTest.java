package com.ryszardpanda.medicalClinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryszardpanda.medicalClinic.model.ChangePasswordDTO;
import com.ryszardpanda.medicalClinic.model.Patient;
import com.ryszardpanda.medicalClinic.model.PatientEditDTO;
import com.ryszardpanda.medicalClinic.service.PatientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    @Test
    void getPatients_PatientsListExist_PatientsReturned() throws Exception {
        Patient patient1 = new Patient(1L, "Adam", "Adamski", "asas@op.pl", "1223", "21323", "121312323", LocalDate.of(1999, 11, 11));
        Patient patient2 = new Patient(1L, "Adam", "Adamski", "asas@op.pl", "1223", "21323", "121312323", LocalDate.of(1999, 11, 11));
        Patient patient3 = new Patient(1L, "Adam", "Adamski", "asas@op.pl", "1223", "21323", "121312323", LocalDate.of(1999, 11, 11));

        List<Patient> pateintsList = List.of(patient1, patient2, patient3);
        PageImpl<Patient> patientsPage = new PageImpl<>(pateintsList);

        Mockito.when(patientService.getPatients(PageRequest.of(0, 2))).thenReturn(patientsPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/patients"))
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].firstName").value("Adam"))
                .andExpect(jsonPath("$.content[0].lastName").value("Adamski"))
                .andExpect(jsonPath("$.content[0].email").value("asas@op.pl"))
                .andExpect(jsonPath("$.content[0].idCardNo").value("21323"))
                .andExpect(jsonPath("$.content[0].phoneNumber").value("121312323"))
                .andExpect(jsonPath("$.content[0].birthday").value("1999-11-11"))
                .andExpect(jsonPath("$.content[1].id").value(1L))
                .andExpect(jsonPath("$.content[1].firstName").value("Adam"))
                .andExpect(jsonPath("$.content[1].lastName").value("Adamski"))
                .andExpect(jsonPath("$.content[1].email").value("asas@op.pl"))
                .andExpect(jsonPath("$.content[1].idCardNo").value("21323"))
                .andExpect(jsonPath("$.content[1].phoneNumber").value("121312323"))
                .andExpect(jsonPath("$.content[1].birthday").value("1999-11-11"))
                .andExpect(jsonPath("$.content[2].id").value(1L))
                .andExpect(jsonPath("$.content[2].firstName").value("Adam"))
                .andExpect(jsonPath("$.content[2].lastName").value("Adamski"))
                .andExpect(jsonPath("$.content[2].email").value("asas@op.pl"))
                .andExpect(jsonPath("$.content[2].idCardNo").value("21323"))
                .andExpect(jsonPath("$.content[2].phoneNumber").value("121312323"))
                .andExpect(jsonPath("$.content[2].birthday").value("1999-11-11"))
                .andExpect(jsonPath("$.totalElements").value(3));
    }

    @Test
    void addPatient_PayloadCorrect_PatientAdded() throws Exception {
        Patient patient1 = new Patient(1L, "Adam", "Adamski", "asas@op.pl", "1223", "21323", "121312323", LocalDate.of(1999, 11, 11));
        PatientEditDTO patientEditDTO = new PatientEditDTO("Adam", "Adamski", "asas@op.pl", "1223", "21323", "121312323", LocalDate.of(1999, 11, 11));

        Mockito.when(patientService.addPatient(any())).thenReturn(patient1);

        mockMvc.perform(post("/patients")
                .content(objectMapper.writeValueAsString(patientEditDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Adam"))
                .andExpect(jsonPath("$.lastName").value("Adamski"))
                .andExpect(jsonPath("$.email").value("asas@op.pl"))
                .andExpect(jsonPath("$.idCardNo").value("21323"))
                .andExpect(jsonPath("$.phoneNumber").value("121312323"))
                .andExpect(jsonPath("$.birthday").value("1999-11-11"));
    }

    @Test
    void getPatientByEmail_PatientExist_PatientReturned() throws Exception {
        Patient patient1 = new Patient(1L, "Adam", "Adamski", "asas@op.pl", "1223", "21323", "121312323", LocalDate.of(1999, 11, 11));

        Mockito.when(patientService.getPatientByEmail(any())).thenReturn(patient1);

        mockMvc.perform(MockMvcRequestBuilders.get("/patients/asas@op.pl"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Adam"))
                .andExpect(jsonPath("$.lastName").value("Adamski"))
                .andExpect(jsonPath("$.email").value("asas@op.pl"))
                .andExpect(jsonPath("$.idCardNo").value("21323"))
                .andExpect(jsonPath("$.phoneNumber").value("121312323"))
                .andExpect(jsonPath("$.birthday").value("1999-11-11"));
    }

    @Test
    void deletePatientByEmail_PatientExist_PatientDeleted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/asas@op.pl"))
                .andExpect(status().isNoContent());
    }

    @Test
    void updatePatient_PatientExist_PatientUpdated() throws Exception {

        Patient patient1 = new Patient(1L, "Adam", "Adamski", "asas@op.pl", "1223", "21323", "121312323", LocalDate.of(1999, 11, 11));

        PatientEditDTO patientEditDTO = new PatientEditDTO("Adammm", "Adamskimmm", "asas@op.pl", "1223", "21323", "121312323", LocalDate.of(1999, 11, 11));

        Patient updatedPatient = new Patient(1L, "Adammm", "Adamskimmm", "asas@op.pl", "1223", "21323", "121312323", LocalDate.of(1999, 11, 11));
        Mockito.when(patientService.updatePatient(patient1.getEmail(), patientEditDTO)).thenReturn(updatedPatient);

        mockMvc.perform(put("/patients/asas@op.pl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientEditDTO)))
                .andExpect(jsonPath("$.firstName").value("Adammm"))
                .andExpect(jsonPath("$.lastName").value("Adamskimmm"))
                .andExpect(jsonPath("$.email").value("asas@op.pl"))
                .andExpect(jsonPath("$.idCardNo").value("21323"))
                .andExpect(jsonPath("$.phoneNumber").value("121312323"))
                .andExpect(jsonPath("$.birthday").value("1999-11-11"));
    }

    @Test
    void updatePassword_PatientExist_PasswordUpdated() throws Exception {
        Patient patient1 = new Patient(1L, "Adam", "Adamski", "asas@op.pl", "1223", "21323", "121312323", LocalDate.of(1999, 11, 11));
        Patient updatedPatient = new Patient(1L, "Adam", "Adamski", "asas@op.pl", "eloelo", "21323", "121312323", LocalDate.of(1999, 11, 11));

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("eloelo");

        Mockito.when(patientService.updatePassword(patient1.getEmail(), changePasswordDTO)).thenReturn(updatedPatient);

        mockMvc.perform(patch("/patients/asas@op.pl")
                .content(objectMapper.writeValueAsString(changePasswordDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
