package ru.nsu.nextples.ms_projects.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.nextples.ms_projects.dto.project.ProjectCreateDTO;
import ru.nsu.nextples.ms_projects.dto.project.ProjectDTO;
import ru.nsu.nextples.ms_projects.dto.project.ProjectUpdateDTO;
import ru.nsu.nextples.ms_projects.exception.ObjectNotFoundException;
import ru.nsu.nextples.ms_projects.model.Project;
import ru.nsu.nextples.ms_projects.model.ProjectStatus;
import ru.nsu.nextples.ms_projects.repository.ProjectRepository;
import ru.nsu.nextples.ms_projects.repository.specifications.ProjectSpecifications;

import java.time.LocalDate;
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
        project.setProgress(0);

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
        Project updatedProject = projectRepository.save(project);
        return mapToDTO(updatedProject, true);
    }

    @Transactional(readOnly = true)
    public ProjectDTO getProject(UUID id) {
        Project project = projectRepository.findOne(ProjectSpecifications.notDeleted(id))
                .orElseThrow(() -> new ObjectNotFoundException("Project", id));

        return mapToDTO(project, true);
    }

    public void deleteProject(UUID id) {
        Project project = projectRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        project.setDeleted(true);
        projectRepository.save(project);
    }

    public void addEmployeeToProject(UUID projectId, UUID employeeId) {
        Project project = getProjectEntity(projectId);
        if (employeeServiceClient.existsEmployee(employeeId)) {
            project.getEmployeeIds().add(employeeId);
            projectRepository.save(project);
        } else {
            throw new ValidationException("Employee does not exist");
        }
    }

    // Удаление сотрудника из проекта
    public void removeEmployeeFromProject(UUID projectId, UUID employeeId) {
        Project project = getProjectEntity(projectId);
        project.getEmployeeIds().remove(employeeId);
        projectRepository.save(project);
    }

    // Добавление оборудования (аналогично сотрудникам)
    public void addEquipmentToProject(UUID projectId, UUID equipmentId) {
        Project project = getProjectEntity(projectId);
        if (equipmentServiceClient.existsEquipment(equipmentId)) {
            project.getEquipmentIds().add(equipmentId);
            projectRepository.save(project);
        } else {
            throw new ValidationException("Equipment does not exist");
        }
    }

    // Обновление внутреннего прогресса (выполняемого компанией)
    public void updateInternalProgress(UUID projectId, int progress) {
        if (progress < 0 || progress > 100) {
            throw new ValidationException("Progress must be between 0 and 100");
        }
        Project project = getProjectEntity(projectId);
        project.setInternalProgress(progress);
        recalculateTotalProgress(project);
        projectRepository.save(project);
    }


    public static ProjectDTO mapToDTO(Project entity, boolean detailed) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setProgress(entity.getProgress());
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