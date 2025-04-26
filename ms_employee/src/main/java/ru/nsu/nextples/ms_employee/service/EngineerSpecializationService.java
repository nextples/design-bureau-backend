package ru.nsu.nextples.ms_employee.service;

import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_employee.dto.EngineerSpecializationDTO;
import ru.nsu.nextples.ms_employee.model.EngineerSpecialization;

@Service
public class EngineerSpecializationService {

    public static EngineerSpecializationDTO mapToDTO(EngineerSpecialization specialization) {
        EngineerSpecializationDTO dto = new EngineerSpecializationDTO();
        dto.setId(specialization.getId());
        dto.setName(specialization.getName());
        return dto;
    }
}
