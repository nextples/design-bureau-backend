package ru.nsu.nextples.ms_employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.nextples.ms_employee.dto.laboratory.LaboratoryCreateUpdateDTO;
import ru.nsu.nextples.ms_employee.dto.laboratory.LaboratoryDTO;
import ru.nsu.nextples.ms_employee.exception.LaboratoryNotFoundException;
import ru.nsu.nextples.ms_employee.model.Laboratory;
import ru.nsu.nextples.ms_employee.repository.LaboratoryRepository;
import ru.nsu.nextples.ms_employee.repository.specifications.LaboratorySpecifications;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LaboratoryService {

    private final LaboratoryRepository laboratoryRepository;

    @Transactional
    public LaboratoryDTO creteLaboratory(LaboratoryCreateUpdateDTO request) {
        Laboratory laboratory = new Laboratory();
        laboratory.setName(request.getName());
        Laboratory savedLaboratory = laboratoryRepository.save(laboratory);
        return mapToDTO(savedLaboratory);
    }

    @Transactional
    public LaboratoryDTO updateLaboratory(UUID id, LaboratoryCreateUpdateDTO request) {
        Laboratory laboratory = laboratoryRepository.findById(id)
                .orElseThrow(() -> new LaboratoryNotFoundException(id));

        laboratory.setName(request.getName());
        Laboratory savedLaboratory = laboratoryRepository.save(laboratory);
        return mapToDTO(savedLaboratory);
    }

    @Transactional(readOnly = true)
    public Page<LaboratoryDTO> getLaboratories(UUID id, String name, Pageable pageable) {
        Specification<Laboratory> spec = Specification.where(null);
        if (id != null) {
            spec.and(LaboratorySpecifications.hasId(id));
        }
        if (name != null) {
            spec.and(LaboratorySpecifications.nameContains(name));
        }

        return laboratoryRepository.findAll(spec, pageable)
                .map(LaboratoryService::mapToDTO);
    }

    @Transactional
    public void deleteLaboratory(UUID id) {
        if (!laboratoryRepository.existsById(id)) {
            throw new LaboratoryNotFoundException(id);
        }
        laboratoryRepository.deleteById(id);
    }

    public static LaboratoryDTO mapToDTO(Laboratory lab) {
        LaboratoryDTO dto = new LaboratoryDTO();
        dto.setId(lab.getId());
        dto.setName(lab.getName());
        return dto;
    }
}
