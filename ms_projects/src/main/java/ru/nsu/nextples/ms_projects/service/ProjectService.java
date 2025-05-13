package ru.nsu.nextples.ms_projects.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.nextples.ms_projects.dto.equipment.AddEquipmentRequestDTO;
import ru.nsu.nextples.ms_projects.dto.project.ProjectCreateDTO;
import ru.nsu.nextples.ms_projects.dto.project.ProjectDTO;
import ru.nsu.nextples.ms_projects.dto.project.ProjectUpdateDTO;
import ru.nsu.nextples.ms_projects.exception.ObjectNotFoundException;
import ru.nsu.nextples.ms_projects.model.Project;
import ru.nsu.nextples.ms_projects.model.ProjectStatus;
import ru.nsu.nextples.ms_projects.model.SubcontractorWork;
import ru.nsu.nextples.ms_projects.repository.ProjectRepository;
import ru.nsu.nextples.ms_projects.repository.specifications.ProjectSpecifications;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectService {

    private final ExternalService externalService;
    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectDTO createProject(ProjectCreateDTO request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStatus(ProjectStatus.IN_PROGRESS);
        project.setStartDate(LocalDate.now());
        project.setEndDate(null);
        project.setCost(request.getCost());
        project.setResponsibleDepartmentId(request.getResponsibleDepartmentId());
        project.setInternalProgress(0);
        project.setTotalProgress(0);

        Project savedProject = projectRepository.save(project);
        return mapToDTO(savedProject, true);
    }

    @Transactional
    public ProjectDTO updateProject(UUID id, ProjectUpdateDTO request) {
        Project project = projectRepository.findOne(ProjectSpecifications.notDeleted(id))
                .orElseThrow(() -> new ObjectNotFoundException("Project", id));

        if (request.getName() != null) {
            project.setName(request.getName());
        }
        if (request.getDescription() != null) {
            project.setDescription(request.getDescription());
        }
        if (request.getResponsibleDepartmentId() != null) {
            project.setResponsibleDepartmentId(request.getResponsibleDepartmentId());
        }
        if (request.getCost() != null) {
            project.setCost(request.getCost());
        }
        Project updatedProject = projectRepository.save(project);
        return mapToDTO(updatedProject, true);
    }

    @Transactional(readOnly = true)
    public List<ProjectDTO> getAllProjects(ProjectStatus status,
                                           LocalDate startDate,
                                           LocalDate endDate,
                                           BigDecimal minCost,
                                           BigDecimal maxCost,
                                           UUID contractId
    ) {
        Specification<Project> filterSpec = Specification.where(null);
        if (status != null) {
            filterSpec = filterSpec.and(ProjectSpecifications.hasStatus(status));
        }
        if (startDate != null) {
            filterSpec = filterSpec.and(ProjectSpecifications.hasDateFrom(startDate));
        }
        if (endDate != null) {
            filterSpec = filterSpec.and(ProjectSpecifications.hasDateFrom(endDate));
        }
        if (minCost != null) {
            filterSpec = filterSpec.and(ProjectSpecifications.hasCostFrom(minCost));
        }
        if (maxCost != null) {
            filterSpec = filterSpec.and(ProjectSpecifications.hasCostFrom(maxCost));
        }
        if (contractId != null) {
            filterSpec = filterSpec.and(ProjectSpecifications.hasContract(contractId));
        }
        List<Project> projects = projectRepository.findAll(filterSpec);
        return projects
                .stream()
                .map(project -> mapToDTO(project, false))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectDTO getProject(UUID id) {
        Project project = projectRepository.findOne(ProjectSpecifications.notDeleted(id))
                .orElseThrow(() -> new ObjectNotFoundException("Project", id));

        return mapToDTO(project, true);
    }

    @Transactional
    public void deleteProject(UUID id) {
        Project project = projectRepository.findOne(ProjectSpecifications.notDeleted(id))
                .orElseThrow(() -> new ObjectNotFoundException("Project", id));

        project.setIsDeleted(true);
        project.getSubcontractorWorks().forEach(work -> work.setIsDeleted(true));

        projectRepository.save(project);
    }

    @Transactional
    public ProjectDTO addEmployeesToProject(UUID projectId, List<UUID> employeeIds) {
        Project project = projectRepository.findOne(ProjectSpecifications.notDeleted(projectId))
                .orElseThrow(() -> new ObjectNotFoundException("Project", projectId));

        externalService.checkEmployeeExists(employeeIds);

        project.getEmployeeIds().addAll(employeeIds);
        Project savedProject = projectRepository.save(project);
        return mapToDTO(savedProject, true);
    }

    @Transactional
    public ProjectDTO removeEmployeeFromProject(UUID projectId, UUID employeeId) {
        Project project = projectRepository.findOne(ProjectSpecifications.notDeleted(projectId))
                .orElseThrow(() -> new ObjectNotFoundException("Project", projectId));

        if (project.getEmployeeIds().remove(employeeId)) {
            Project savedProject = projectRepository.save(project);
            return mapToDTO(savedProject, true);
        }
        return mapToDTO(project, true);
    }

    @Transactional
    public ProjectDTO addEquipmentToProject(UUID projectId, AddEquipmentRequestDTO request) {
        Project project = projectRepository.findOne(ProjectSpecifications.notDeleted(projectId))
                .orElseThrow(() -> new ObjectNotFoundException("Project", projectId));

        externalService.checkEquipmentExists(request.getEquipmentIds());
        for (UUID equipmentId : request.getEquipmentIds()) {
            externalService.assignEquipmentToProject(equipmentId, project, request.getPurpose());
        }

        project.getEquipmentIds().addAll(request.getEquipmentIds());
        Project savedProject = projectRepository.save(project);
        return mapToDTO(savedProject, true);
    }

    @Transactional
    public ProjectDTO removeEquipmentFromProject(UUID projectId, UUID equipmentId) {
        Project project = projectRepository.findOne(ProjectSpecifications.notDeleted(projectId))
                .orElseThrow(() -> new ObjectNotFoundException("Project", projectId));

        if (project.getEquipmentIds().remove(equipmentId)) {
            Project savedProject = projectRepository.save(project);
            return mapToDTO(savedProject, true);
        }
        return mapToDTO(project, true);
    }

    @Transactional
    public ProjectDTO updateInternalProgress(UUID projectId, int newProgress) {
        Project project = projectRepository.findOne(ProjectSpecifications.notDeleted(projectId))
                .orElseThrow(() -> new ObjectNotFoundException("Project", projectId));

        project.setInternalProgress(newProgress);
        recalculateTotalProgress(project);

        Project savedProject = projectRepository.save(project);
        return mapToDTO(savedProject, false);
    }

    @Transactional(readOnly = true)
    public List<UUID> getProjectEmployees(UUID projectId) {
        Project project = projectRepository.findOne(ProjectSpecifications.notDeleted(projectId))
                .orElseThrow(() -> new ObjectNotFoundException("Project", projectId));

        return project.getEmployeeIds();
    }

    public static void recalculateTotalProgress(Project project) {
        int external = project.getSubcontractorWorks().stream()
                .mapToInt(work -> (work.getWorkPercentage() * work.getProgress()) / 100)
                .sum();

        int internal = (project.getInternalProgress() * (100 - getTotalSubcontractedPercentage(project))) / 100;

        project.setTotalProgress(internal + external);
    }

    // Суммарная доля работ, переданных субподрядчикам
    public static int getTotalSubcontractedPercentage(Project project) {
        return project.getSubcontractorWorks().stream()
                .mapToInt(SubcontractorWork::getWorkPercentage)
                .sum();
    }

    public static ProjectDTO mapToDTO(Project entity, boolean detailed) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setResponsibleDepartmentId(entity.getResponsibleDepartmentId());
        dto.setInternalProgress(entity.getInternalProgress());
        dto.setTotalProgress(entity.getTotalProgress());
        if (detailed) {
            dto.setEmployeeIds(entity.getEmployeeIds());
            dto.setEquipmentIds(entity.getEquipmentIds());
            dto.setContractIds(entity.getContractIds());
            dto.setSubcontractorWorks(entity.getSubcontractorWorks()
                    .stream()
                    .map(SubcontractorWorkService::mapToDTO)
                    .collect(Collectors.toList())
            );
        }
        return dto;
    }
}