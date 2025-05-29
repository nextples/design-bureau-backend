package ru.nsu.nextples.ms_projects.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.nextples.ms_projects.dto.project.ProjectDTO;
import ru.nsu.nextples.ms_projects.dto.subcontractor.SubcontractorWorkDTO;
import ru.nsu.nextples.ms_projects.exception.ObjectNotFoundException;
import ru.nsu.nextples.ms_projects.exception.PercentageException;
import ru.nsu.nextples.ms_projects.model.Project;
import ru.nsu.nextples.ms_projects.model.Subcontractor;
import ru.nsu.nextples.ms_projects.model.SubcontractorWork;
import ru.nsu.nextples.ms_projects.repository.ProjectRepository;
import ru.nsu.nextples.ms_projects.repository.SubcontractorRepository;
import ru.nsu.nextples.ms_projects.repository.SubcontractorWorkRepository;
import ru.nsu.nextples.ms_projects.repository.specifications.ProjectSpecifications;
import ru.nsu.nextples.ms_projects.repository.specifications.SubcontractorSpecifications;
import ru.nsu.nextples.ms_projects.repository.specifications.SubcontractorWorkSpecifications;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubcontractorWorkService {

    private final SubcontractorWorkRepository subcontractorWorkRepository;
    private final SubcontractorRepository subcontractorRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public SubcontractorWorkDTO assignWorkToSubcontractor(UUID projectId, UUID subcontractorId, int workPercentage) {
        Project project = projectRepository.findOne(ProjectSpecifications.notDeleted(projectId))
                .orElseThrow(() -> new ObjectNotFoundException("Project", projectId));
        Subcontractor subcontractor = subcontractorRepository.findOne(SubcontractorSpecifications.notDeleted(subcontractorId))
                .orElseThrow(() -> new ObjectNotFoundException("Subcontractor", subcontractorId));

        int totalSubcontracted = project.getSubcontractorWorks().stream()
                .mapToInt(SubcontractorWork::getWorkPercentage)
                .sum() + workPercentage;

        if (totalSubcontracted > 100) {
            throw new PercentageException("Total subcontracted work cannot exceed 100%");
        }

        SubcontractorWork work = new SubcontractorWork();
        work.setProject(project);
        work.setSubcontractor(subcontractor);
        work.setWorkPercentage(workPercentage);
        work.setProgress(0);
        SubcontractorWork savedWork = subcontractorWorkRepository.save(work);
        return mapToDTO(savedWork);
    }

    @Transactional
    public SubcontractorWorkDTO updateSubcontractorProgress(UUID workId, int progress) {
        SubcontractorWork work = subcontractorWorkRepository.findOne(SubcontractorWorkSpecifications.notDeleted(workId))
                .orElseThrow(() -> new ObjectNotFoundException("Subcontractor work", workId));
        work.setProgress(progress);

        Project project = work.getProject();
        ProjectService.recalculateTotalProgress(project);
        projectRepository.save(project);
        SubcontractorWork savedWork = subcontractorWorkRepository.save(work);
        return mapToDTO(savedWork);
    }

    @Transactional(readOnly = true)
    public List<SubcontractorWorkDTO> getAllSubcontractedWorks() {
        List<SubcontractorWorkDTO> works = new ArrayList<>();
        for (SubcontractorWork work : subcontractorWorkRepository.findAll()) {
            works.add(mapToDTO(work));
        }
        return works;
    }

    public static SubcontractorWorkDTO mapToDTO(SubcontractorWork entity) {
        SubcontractorWorkDTO dto = new SubcontractorWorkDTO();
        dto.setId(entity.getId());
        dto.setWorkPercentage(entity.getWorkPercentage());
        dto.setProgress(entity.getProgress());
        dto.setProject(ProjectService.mapToDTO(entity.getProject(), false));
        dto.setSubcontractor(SubcontractorService.mapToDTO(entity.getSubcontractor(), false));
        BigDecimal cost = entity.getProject().getCost().multiply(BigDecimal.valueOf(entity.getWorkPercentage() / 100.0));
        dto.setCost(cost);
        return dto;
    }
}
