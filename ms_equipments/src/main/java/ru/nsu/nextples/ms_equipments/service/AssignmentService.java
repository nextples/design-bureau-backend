package ru.nsu.nextples.ms_equipments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.nextples.ms_equipments.dto.assignment.AssignmentDTO;
import ru.nsu.nextples.ms_equipments.dto.assignment.AssignmentRequestDTO;
import ru.nsu.nextples.ms_equipments.dto.assignment.ReturnRequestDTO;
import ru.nsu.nextples.ms_equipments.exception.DoubleReturnException;
import ru.nsu.nextples.ms_equipments.exception.ObjectNotFoundException;
import ru.nsu.nextples.ms_equipments.exception.UnavailableObjectStateException;
import ru.nsu.nextples.ms_equipments.model.Assignment;
import ru.nsu.nextples.ms_equipments.model.Equipment;
import ru.nsu.nextples.ms_equipments.repository.AssignmentRepository;
import ru.nsu.nextples.ms_equipments.repository.EquipmentRepository;
import ru.nsu.nextples.ms_equipments.repository.specifications.EquipmentSpecifications;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final EquipmentRepository equipmentRepository;
    private final ExternalService externalService;

    @Transactional
    public AssignmentDTO assignToProject(UUID equipmentId, AssignmentRequestDTO request) {
        externalService.checkProjectExists(request.getProjectId());

        Equipment equipment = equipmentRepository.findOne(
                        EquipmentSpecifications.notDeleted(equipmentId)
                )
                .orElseThrow(() -> new ObjectNotFoundException("Equipment", equipmentId));

        if (!equipment.isAvailable()) {
            throw new UnavailableObjectStateException("Equipment is not available to assign");
        }

        equipment.setAvailable(false);
        equipment.setCurrentProjectId(request.getProjectId());
        equipment.setCurrentDepartmentId(request.getResponsibleDepartmentId());
        equipmentRepository.save(equipment);

        Assignment assignment = new Assignment();
        assignment.setEquipment(equipment);
        assignment.setStartDate(LocalDate.now());
        assignment.setPurpose(request.getPurpose());
        assignment.setProjectId(request.getProjectId());
        assignment.setDepartmentId(request.getResponsibleDepartmentId());

        return mapToDTO(assignmentRepository.save(assignment));
    }

    @Transactional
    public AssignmentDTO returnFromProject(UUID assignmentId, ReturnRequestDTO request) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ObjectNotFoundException("Assignment", assignmentId));

        if (assignment.getEndDate() != null) {
            throw new DoubleReturnException("Equipment was already returned by this assignment");
        }

        assignment.setEndDate(LocalDate.now());
        assignment.setHoursUsed(request.getHoursUsed());
        Equipment assignedEquipment = assignment.getEquipment();
        assignedEquipment.setCurrentProjectId(null);
        assignedEquipment.setCurrentDepartmentId(null);
        assignedEquipment.setAvailable(true);
        equipmentRepository.save(assignedEquipment);

        return mapToDTO(assignmentRepository.save(assignment));
    }

    @Transactional
    public Page<AssignmentDTO> getAssignmentHistory(
            UUID equipmentId,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    ) {
        Specification<Assignment> spec = Specification.where(
                (root, query, cb) ->
                        cb.equal(root.get("equipment").get("id"), equipmentId)
        );

        if (startDate != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("startDate"), startDate)
            );
        }

        if (endDate != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("endDate"), endDate)
            );
        }

        return assignmentRepository.findAll(spec, pageable).map(AssignmentService::mapToDTO);
    }

    public static AssignmentDTO mapToDTO(Assignment entity) {
        AssignmentDTO dto = new AssignmentDTO();
        dto.setId(entity.getId());
        dto.setEquipment(EquipmentService.mapToDTO(entity.getEquipment(), false));
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setHoursUsed(entity.getHoursUsed());
        dto.setPurpose(entity.getPurpose());
        dto.setDepartmentId(entity.getDepartmentId());
        dto.setProjectId(entity.getProjectId());
        return dto;
    }
}
