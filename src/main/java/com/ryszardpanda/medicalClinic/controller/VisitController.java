package com.ryszardpanda.medicalClinic.controller;

import com.ryszardpanda.medicalClinic.mapper.VisitMapper;
import com.ryszardpanda.medicalClinic.model.Visit;
import com.ryszardpanda.medicalClinic.model.VisitDTO;
import com.ryszardpanda.medicalClinic.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;
    private final VisitMapper visitMapper;

    @PostMapping("/create")
    public VisitDTO createVisit(@Valid @RequestParam Long doctorId, @Valid @RequestParam LocalDateTime dateTime) {
        return visitMapper.visitToVisitDTO(visitService.createVisit(doctorId, dateTime));
    }

    @PatchMapping("/{visitId}/book")
    public VisitDTO bookVisit(@Valid @PathVariable Long visitId, @Valid @RequestParam Long patientId) {
        return visitMapper.visitToVisitDTO(visitService.bookVisit(visitId, patientId));
    }

    @GetMapping("/patient/{patientId}")
    public List<VisitDTO> getPatientVisits(@Valid @PathVariable Long patientId) {
        return visitService.getPatientsVisits(patientId).stream()
                .map(visitMapper::visitToVisitDTO).toList();
    }

    @GetMapping("/available")
    public List<VisitDTO> getAvailableVisits() {
        return visitService.getAvailableVisit().stream()
                .map(visitMapper::visitToVisitDTO).toList();
    }
}
