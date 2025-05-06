package ru.nsu.nextples.ms_equipments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentCreateDTO;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentDTO;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentUpdateDTO;
import ru.nsu.nextples.ms_equipments.exception.DeleteConflictException;
import ru.nsu.nextples.ms_equipments.exception.ObjectNotFoundException;
import ru.nsu.nextples.ms_equipments.model.*;
import ru.nsu.nextples.ms_equipments.repository.AssignmentRepository;
import ru.nsu.nextples.ms_equipments.repository.EquipmentRepository;
import ru.nsu.nextples.ms_equipments.repository.EquipmentTypeRepository;
import ru.nsu.nextples.ms_equipments.repository.specifications.EquipmentSpecifications;
import ru.nsu.nextples.ms_equipments.repository.specifications.EquipmentTypeSpecifications;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EquipmentService {

    private final ExternalService externalService;
    private final EquipmentRepository equipmentRepository;
    private final EquipmentTypeRepository typeRepository;
    private final AssignmentRepository assignmentRepository;

    @Transactional(readOnly = true)
    public Boolean checkIfEquipmentExists(UUID id) {
        return equipmentRepository.exists(EquipmentSpecifications.notDeleted(id));
    }

    @Transactional
    public EquipmentDTO createEquipment(EquipmentCreateDTO request) {
        EquipmentType type = typeRepository.findOne(
                        EquipmentTypeSpecifications.notDeleted(request.getEquipmentTypeId())
                )
                .orElseThrow(() -> new ObjectNotFoundException("Equipment Type", request.getEquipmentTypeId()));

        externalService.checkDepartmentExists(request.getInitialDepartmentOwnerId());

        Equipment equipment = new Equipment();
        equipment.setName(request.getName());
        equipment.setSerialNumber(request.getSerialNumber());
        equipment.setType(type);
        equipment.setPurchaseDate(request.getPurchaseDate());
        equipment.setCurrentDepartmentId(request.getInitialDepartmentOwnerId());
        equipment.setCurrentProjectId(null);
        equipment.setShared(request.getIsShared());

        Equipment savedEquipment = equipmentRepository.save(equipment);
        return mapToDTO(savedEquipment, true);
    }

    @Transactional
    public EquipmentDTO updateEquipment(UUID id, EquipmentUpdateDTO request) {
        Equipment equipment = equipmentRepository.findOne(EquipmentSpecifications.notDeleted(id))
                .orElseThrow(() -> new ObjectNotFoundException("Equipment", id));

        if (equipment.getName() != null) {
            equipment.setName(request.getName());
        }
        if (equipment.getSerialNumber() != null) {
            equipment.setSerialNumber(request.getSerialNumber());
        }
        if (request.getEquipmentTypeId() != null) {
            EquipmentType type = typeRepository.findById(request.getEquipmentTypeId())
                    .orElseThrow(() -> new ObjectNotFoundException("Equipment", request.getEquipmentTypeId()));
            equipment.setType(type);
        }
        if (request.getPurchaseDate() != null) {
            equipment.setPurchaseDate(request.getPurchaseDate());
        }
        if (request.getIsShared() != null) {
            equipment.setShared(request.getIsShared());
        }
        Equipment savedEquipment = equipmentRepository.save(equipment);
        return mapToDTO(savedEquipment, true);
    }

    @Transactional(readOnly = true)
    public Page<EquipmentDTO> getEquipments(String name,
                                            UUID departmentId,
                                            UUID projectId,
                                            UUID typeId,
                                            Pageable pageable
    ) {
        Specification<Equipment> spec = Specification.where(null);
        if (name != null) {
            spec = spec.and(EquipmentSpecifications.nameContains(name));
        }
        if (typeId != null) {
            spec = spec.and(EquipmentSpecifications.hasTypeId(typeId));
        }
        if (departmentId != null) {
            spec = spec.and(EquipmentSpecifications.hasDepartmentId(departmentId));
        }
        if (projectId != null) {
            spec = spec.and(EquipmentSpecifications.hasProjectId(projectId));
        }
        spec.and(EquipmentSpecifications.notDeleted());

        Page<Equipment> equipments = equipmentRepository.findAll(spec, pageable);
        return equipments.map(equipment -> mapToDTO(equipment, false));
    }

    @Transactional(readOnly = true)
    public EquipmentDTO getEquipmentDetails(UUID id) {
        Specification<Equipment> existSpec = Specification.where(EquipmentSpecifications.hasId(id))
                .and(EquipmentSpecifications.notDeleted());
        Equipment equipment = equipmentRepository.findOne(existSpec)
                .orElseThrow(() -> new ObjectNotFoundException("Equipment", id));

        return mapToDTO(equipment, true);
    }

    @Transactional
    public void softDeleteEquipment(UUID id) {
        Specification<Equipment> safeDeleteSpec = Specification.where(
                        EquipmentSpecifications.hasId(id)
                ).and(EquipmentSpecifications.notDeleted())
                .and(EquipmentSpecifications.hasNoActiveDependencies());

        Equipment equipment = equipmentRepository.findOne(safeDeleteSpec)
                .orElseThrow(() -> {
                    boolean exists = equipmentRepository.existsById(id);
                    if (!exists) {
                        return new ObjectNotFoundException("Equipment", id);
                    }
                    return new DeleteConflictException("Equipment", id);
                });

        equipment.setDeleted(true);
        equipmentRepository.save(equipment);
    }

    public static EquipmentDTO mapToDTO(Equipment entity, boolean detailed) {
        EquipmentDTO dto = new EquipmentDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
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