package com.ryszardpanda.medicalClinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryszardpanda.medicalClinic.model.*;
import com.ryszardpanda.medicalClinic.service.VisitService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class VisitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VisitService visitService;

    //nie pasi mu
    @Test
    void createVisit_PayloadCorrect_VisitCreated() throws Exception {
        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());
        Institution institution = new Institution(1L, "Marcinkowska", new HashSet<Doctor>());
        VisitEditDTO visitEditDTO = new VisitEditDTO(1L, LocalDateTime.of(2025, 2, 12, 11, 30),
                LocalDateTime.of(2025, 2, 12, 11, 41),
                doctor.getId(), institution.getId());

        Mockito.when(visitService.createVisit(visitEditDTO)).thenReturn(Visit.of(visitEditDTO, doctor, institution));

        mockMvc.perform(MockMvcRequestBuilders.post("/visits")
                        .content(objectMapper.writeValueAsString(visitEditDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.startDate").value("2025-02-12T11:30:00"))
                .andExpect(jsonPath("$.endDate").value("2025-02-12T11:41:00"))
                .andExpect(jsonPath("$.doctorId").value(1L))
                .andExpect(jsonPath("$.institutionId").value(1L));
    }

    @Test
    void bookVisit_VisitAndPatientExist_VisitBooked() throws Exception {
        Patient patient1 = new Patient(1L, "Adam", "Adamski", "asas@op.pl", "1223", "21323", "121312323", LocalDate.of(1999, 11, 11));
        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());
        Institution institution = new Institution(1L, "Marcinkowska", new HashSet<Doctor>());
        VisitEditDTO visitEditDTO = new VisitEditDTO(1L, LocalDateTime.of(2025, 2, 12, 11, 30),
                LocalDateTime.of(2025, 2, 12, 11, 41),
                doctor.getId(), institution.getId());

        Visit bookedVisit = new Visit(1L, LocalDateTime.of(2025, 11, 11, 11, 15),
                LocalDateTime.of(2025, 11, 11, 12, 45), patient1, doctor, institution);

        Mockito.when(visitService.bookVisit(visitEditDTO.getId(), patient1.getId())).thenReturn(bookedVisit);

        mockMvc.perform(MockMvcRequestBuilders.patch("/visits/1/book")
                        .param("patientId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                //.andExpect(jsonPath("$.visitId").value(1L))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.startDate").value("2025-11-11T11:15:00"))
                .andExpect(jsonPath("$.endDate").value("2025-11-11T12:45:00"))
                .andExpect(jsonPath("$.doctorId").value(1L))
                .andExpect(jsonPath("$.institutionId").value(1L));

    }

    @Test
    void getAvailableVisit_VisitsExists_VisitsListReturned() throws Exception {
        Patient patient1 = new Patient(1L, "Adam", "Adamski", "asas@op.pl", "1223", "21323", "121312323", LocalDate.of(1999, 11, 11));
        Doctor doctor = new Doctor(1L, "Marcin", "Macinowski", "marcinn@op.pl", "eloelo", "kombinatoryka", new HashSet<Institution>());
        Institution institution = new Institution(1L, "Marcinkowska", new HashSet<Doctor>());

        Visit visit1 = new Visit(1L, LocalDateTime.of(2025, 11, 11, 11, 15),
                LocalDateTime.of(2025, 11, 11, 12, 45), patient1, doctor, institution);
        Visit visit2 = new Visit(1L, LocalDateTime.of(2025, 11, 11, 11, 15),
                LocalDateTime.of(2025, 11, 11, 12, 45), patient1, doctor, institution);
        Visit visit3 = new Visit(1L, LocalDateTime.of(2025, 11, 11, 11, 15),
                LocalDateTime.of(2025, 11, 11, 12, 45), patient1, doctor, institution);

        List<Visit> visitsList = List.of(visit1, visit2, visit3);

        Mockito.when(visitService.getAvailableVisit()).thenReturn(visitsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/visits/available"))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].startDate").value("2025-11-11T11:15:00"))
                .andExpect(jsonPath("$[0].endDate").value("2025-11-11T12:45:00"))
                .andExpect(jsonPath("$[0].doctorId").value(1L))
                .andExpect(jsonPath("$[0].institutionId").value(1L));
    }
}
