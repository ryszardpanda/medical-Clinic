package com.ryszardpanda.medicalClinic.service;

import com.ryszardpanda.medicalClinic.exceptions.NoIdNumberException;
import com.ryszardpanda.medicalClinic.mapper.InstitutionMapper;
import com.ryszardpanda.medicalClinic.model.Institution;
import com.ryszardpanda.medicalClinic.model.InstitutionDTO;
import com.ryszardpanda.medicalClinic.repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

    public Institution addInstitution(InstitutionDTO institutionDTO) {
        Institution institution = institutionMapper.institutionDTOtoInstitution(institutionDTO);
        return institutionRepository.save(institution);
    }

    public Institution findInstitutionById(Long id) {
        return institutionRepository.findById(id).orElseThrow(() -> new NoIdNumberException("Nie znaleziono placówki o takim ID",
                HttpStatus.NOT_FOUND));
    }
}
