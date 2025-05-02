package ru.nsu.nextples.ms_equipments.service;

import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_equipments.dto.assignment.AssignmentDTO;
import ru.nsu.nextples.ms_equipments.dto.assignment.StatusChangeDTO;
import ru.nsu.nextples.ms_equipments.model.Assignment;
import ru.nsu.nextples.ms_equipments.model.DepartmentAssignment;
import ru.nsu.nextples.ms_equipments.model.ProjectAssignment;
import ru.nsu.nextples.ms_equipments.model.StatusChange;

@Service
public class AssignmentService {

    public static AssignmentDTO mapToDTO(Assignment entity) {
        AssignmentDTO dto = new AssignmentDTO();
        dto.setId(entity.getId());
        dto.setEquipment(EquipmentService.mapToDTO(entity.getEquipment(), false));
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setHoursUsed(entity.getHoursUsed());
        dto.setPurpose(entity.getPurpose());
        if (entity instanceof DepartmentAssignment departmentAssignment) {
            dto.setProjectId(departmentAssignment.getDepartmentId());
        }
        if (entity instanceof ProjectAssignment projectAssignment) {
            dto.setProjectId(projectAssignment.getProjectId());
        }
        return dto;
    }

    public static StatusChangeDTO mapToDTO(StatusChange entity) {
        StatusChangeDTO dto = new StatusChangeDTO();
        dto.setId(entity.getId());
        dto.setEquipment(EquipmentService.mapToDTO(entity.getEquipment(), false));
        dto.setOldStatus(entity.getOldStatus());
        dto.setNewStatus(entity.getNewStatus());
        dto.setChangeDate(entity.getChangeDate());
        return dto;
    }
}
