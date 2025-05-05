package ru.nsu.nextples.ms_equipments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentDTO;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentDistributionDTO;
import ru.nsu.nextples.ms_equipments.dto.report.EfficiencyDTO;
import ru.nsu.nextples.ms_equipments.dto.report.UsageDTO;
import ru.nsu.nextples.ms_equipments.exception.ObjectNotFoundException;
import ru.nsu.nextples.ms_equipments.model.Assignment;
import ru.nsu.nextples.ms_equipments.model.DepartmentAssignment;
import ru.nsu.nextples.ms_equipments.model.Equipment;
import ru.nsu.nextples.ms_equipments.model.ProjectAssignment;
import ru.nsu.nextples.ms_equipments.repository.AssignmentRepository;
import ru.nsu.nextples.ms_equipments.repository.EquipmentRepository;
import ru.nsu.nextples.ms_equipments.repository.specifications.AssignmentSpecifications;
import ru.nsu.nextples.ms_equipments.repository.specifications.EquipmentSpecifications;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportService {

    private final EquipmentRepository equipmentRepository;
    private final AssignmentRepository assignmentRepository;
    private final ExternalService externalService;

    @Transactional(readOnly = true)
    public List<EquipmentDistributionDTO> getEquipmentDistribution(LocalDate targetDate) {
        List<Equipment> allEquipment = equipmentRepository.findAll(EquipmentSpecifications.notDeleted());

        return allEquipment.stream()
                .map(e -> mapToDistributionDTO(e, targetDate))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsageDTO> getUsageByProjects(List<UUID> projectIds) {
        List<Assignment> assignments = assignmentRepository.findAll(
                AssignmentSpecifications.hasProjectIds(projectIds)
        );
        List<ProjectAssignment> projectAssignments = assignments.stream()
                .map(assignment -> (ProjectAssignment) assignment)
                .toList();

        return aggregateProjectUsage(projectAssignments);
    }

    @Transactional(readOnly = true)
    public List<UsageDTO> getUsageByContract(UUID contractId) {
        List<UUID> projectIds = externalService.getProjectIdsByContractId(contractId);
        return getUsageByProjects(projectIds);
    }

    @Transactional(readOnly = true)
    public EfficiencyDTO getEfficiency(UUID equipmentId) {
        List<Object[]> result = equipmentRepository.findEquipmentEfficiencyData(equipmentId);

        if (result.isEmpty()) {
            throw new ObjectNotFoundException("Equipment", equipmentId);
        }

        Object[] row = result.getFirst();
        Equipment equipment = (Equipment) row[0];
        Integer totalHours = ((Number) row[1]).intValue();

        EquipmentDTO equipmentDTO = EquipmentService.mapToDTO(equipment, false);
        EfficiencyDTO dto = new EfficiencyDTO();
        dto.setEquipment(equipmentDTO);
        dto.setTotalHoursUsed(totalHours);
        return dto;
    }

    private List<UsageDTO> aggregateProjectUsage(List<ProjectAssignment> assignments) {
        Map<UUID, UsageDTO> usageMap = new HashMap<>();

        for (ProjectAssignment assignment : assignments) {
            UUID projectId = assignment.getProjectId();

            UsageDTO dto = usageMap.computeIfAbsent(projectId, id -> {
                UsageDTO newDto = new UsageDTO();
                newDto.setProjectId(id);
                return newDto;
            });
            EquipmentDTO equipmentDTO = EquipmentService.mapToDTO(assignment.getEquipment(), false);
            dto.getEquipmentStats()
                    .put(equipmentDTO, (assignment.getHoursUsed() != null ? assignment.getHoursUsed() : 0));
        }
        return new ArrayList<>(usageMap.values());
    }

    private EquipmentDistributionDTO mapToDistributionDTO(Equipment equipment, LocalDate targetDate) {
        Optional<Assignment> activeAssignment = assignmentRepository.findOne(
                AssignmentSpecifications.hasActiveAssignment(equipment.getId(), targetDate)
        );

        EquipmentDistributionDTO dto = new EquipmentDistributionDTO();
        dto.setEquipmentId(equipment.getId());
        dto.setEquipmentName(equipment.getName());

        activeAssignment.ifPresent(assignment -> {
            if (assignment instanceof ProjectAssignment) {
                dto.setProjectId(((ProjectAssignment) assignment).getProjectId());
                dto.setAssignmentType("PROJECT");
            } else if (assignment instanceof DepartmentAssignment) {
                dto.setDepartmentId(((DepartmentAssignment) assignment).getDepartmentId());
                dto.setAssignmentType("DEPARTMENT");
            }
            dto.setStartDate(assignment.getStartDate());
            dto.setEndDate(assignment.getEndDate());
        });

        return dto;
    }
}
