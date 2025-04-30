package ru.nsu.nextples.ms_employee.service;

import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_employee.dto.employee.lab_assistant.LaboratoryDTO;
import ru.nsu.nextples.ms_employee.model.Laboratory;

@Service
public class LaboratoryService {

    public static LaboratoryDTO mapToDTO(Laboratory lab) {
        LaboratoryDTO dto = new LaboratoryDTO();
        dto.setId(lab.getId());
        dto.setName(lab.getName());
        return dto;
    }
}
