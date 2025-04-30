package ru.nsu.nextples.ms_employee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.nextples.ms_employee.dto.employee.engineer.EngineerSpecializationCreateUpdateDTO;
import ru.nsu.nextples.ms_employee.dto.employee.engineer.EngineerSpecializationDTO;
import ru.nsu.nextples.ms_employee.exception.SpecializationNotFoundException;
import ru.nsu.nextples.ms_employee.model.EngineerSpecialization;
import ru.nsu.nextples.ms_employee.repository.EngineerSpecializationRepository;
import ru.nsu.nextples.ms_employee.repository.specifications.SpecializationSpecifications;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EngineerSpecializationService {

    private final EngineerSpecializationRepository specializationRepository;

    @Transactional
    public EngineerSpecializationDTO createSpecialization(EngineerSpecializationCreateUpdateDTO request) {
        EngineerSpecialization specialization = new EngineerSpecialization();
        specialization.setName(request.getName());
        EngineerSpecialization savedSpecialization = specializationRepository.save(specialization);

        return mapToDTO(savedSpecialization);
    }

    @Transactional
    public EngineerSpecializationDTO updateSpecialization(UUID id, EngineerSpecializationCreateUpdateDTO request) {
        EngineerSpecialization specialization = specializationRepository.findById(id)
                .orElseThrow(() -> new SpecializationNotFoundException(id));

        specialization.setName(request.getName());
        EngineerSpecialization savedSpecialization = specializationRepository.save(specialization);
        return mapToDTO(savedSpecialization);
    }

    @Transactional(readOnly = true)
    public Page<EngineerSpecializationDTO> getSpecializations(UUID id, String name, Pageable pageable) {
        Specification<EngineerSpecialization> spec = Specification.where(null);
        if (id != null) {
            spec = spec.and(SpecializationSpecifications.hasId(id));
        }
        if (name != null) {
            spec = spec.and(SpecializationSpecifications.nameContains(name));
        }

        return specializationRepository.findAll(spec, pageable)
                .map(EngineerSpecializationService::mapToDTO);
    }

    @Transactional
    public void deleteSpecialization(UUID id) {
        if (!specializationRepository.existsById(id)) {
            throw new SpecializationNotFoundException(id);
        }
        specializationRepository.deleteById(id);
    }

    public static EngineerSpecializationDTO mapToDTO(EngineerSpecialization specialization) {
        EngineerSpecializationDTO dto = new EngineerSpecializationDTO();
        dto.setId(specialization.getId());
        dto.setName(specialization.getName());
        return dto;
    }
}
