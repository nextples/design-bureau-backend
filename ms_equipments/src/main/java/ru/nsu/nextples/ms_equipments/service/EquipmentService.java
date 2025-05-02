package ru.nsu.nextples.ms_equipments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentCreateDTO;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentDTO;
import ru.nsu.nextples.ms_equipments.exception.ObjectNotFoundException;
import ru.nsu.nextples.ms_equipments.model.*;
import ru.nsu.nextples.ms_equipments.repository.EquipmentTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EquipmentService {

    EquipmentTypeRepository typeRepository;

    public EquipmentDTO createEquipment(EquipmentCreateDTO request) {
        EquipmentType type = typeRepository.findById(request.getEquipmentTypeId())
                .orElseThrow(() -> new ObjectNotFoundException("Equipment", request.getEquipmentTypeId()));

        Equipment equipment = new Equipment();
        equipment.setName(request.getName());
        equipment.setSerialNumber(request.getSerialNumber());
        equipment.setType(type);
        equipment.setPurchaseDate(request.getPurchaseDate());
        equipment.setStatus(EquipmentStatus.AVAILABLE);
        //TODO: валидация значения departmentId через клиента к ms_employee
        equipment.setCurrentDepartmentId(request.getInitialDepartmentOwnerId());
        equipment.setCurrentProjectId(null);
        equipment.setShared(request.getIsShared());
        return null;
    }

    public static EquipmentDTO mapToDTO(Equipment entity, boolean detailed) {
        EquipmentDTO dto = new EquipmentDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setCurrentDepartmentId(entity.getCurrentDepartmentId());
        dto.setCurrentProjectId(entity.getCurrentProjectId());
        dto.setEquipmentType(EquipmentTypeService.mapToDTO(entity.getType()));
        if (detailed) {
            dto.setSerialNumber(entity.getSerialNumber());
            dto.setPurchaseDate(entity.getPurchaseDate());
            dto.setIsShared(entity.isShared());
            dto.setDepartmentAssignments(getDepartmentAssignments(entity)
                    .stream()
                    .map(AssignmentService::mapToDTO)
                    .collect(Collectors.toList())
            );
            dto.setProjectAssignments(getProjectAssignments(entity)
                    .stream()
                    .map(AssignmentService::mapToDTO)
                    .collect(Collectors.toList())
            );
            dto.setStatusChanges(entity.getStatusChanges()
                    .stream()
                    .map(AssignmentService::mapToDTO)
                    .collect(Collectors.toList())
            );
            dto.setMaintenanceRecords(entity.getMaintenanceRecords()
                    .stream()
                    .map(MaintenanceService::mapToDTO)
                    .collect(Collectors.toList())
            );
        }
        return dto;
    }

    public static List<DepartmentAssignment> getDepartmentAssignments(Equipment equipment) {
        List<Assignment> assignments = equipment.getAssignments();
        return assignments.stream()
                .filter(a -> a instanceof DepartmentAssignment)
                .map(a -> (DepartmentAssignment) a)
                .collect(Collectors.toList());
    }

    public static List<ProjectAssignment> getProjectAssignments(Equipment equipment) {
        List<Assignment> assignments = equipment.getAssignments();
        return assignments.stream()
                .filter(a -> a instanceof ProjectAssignment)
                .map(a -> (ProjectAssignment) a)
                .collect(Collectors.toList());
    }
}
