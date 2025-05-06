package ru.nsu.nextples.ms_projects.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.nextples.ms_projects.dto.subcontractor.SubcontractorWorkDTO;
import ru.nsu.nextples.ms_projects.model.SubcontractorWork;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubcontractorWorkService {

    public void assignWorkToSubcontractor(UUID projectId, UUID subcontractorId, int workPercentage) {
        Project project = projectRepository.findByIdAndDeletedFalse(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        Subcontractor subcontractor = subcontractorRepository.findById(subcontractorId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcontractor not found"));

        int totalSubcontracted = project.getSubcontractorWorks().stream()
                .mapToInt(SubcontractorWork::getWorkPercentage)
                .sum() + workPercentage;

        if (totalSubcontracted > 100) {
            throw new BusinessLogicException("Total subcontracted work cannot exceed 100%");
        }

        SubcontractorWork work = new SubcontractorWork();
        work.setProject(project);
        work.setSubcontractor(subcontractor);
        work.setWorkPercentage(workPercentage);
        work.setProgress(0);
        subcontractorWorkRepository.save(work);
    }

    // Обновление прогресса субподрядчика
    public void updateSubcontractorProgress(UUID workId, int progress) {
        SubcontractorWork work = subcontractorWorkRepository.findById(workId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcontractor work not found"));
        work.setProgress(progress);
        subcontractorWorkRepository.save(work);

        Project project = work.getProject();
        project.getProjectService().recalculateTotalProgress(project);
        projectRepository.save(project);
    }

    public static SubcontractorWorkDTO mapToDTO(SubcontractorWork entity) {
        SubcontractorWorkDTO dto = new SubcontractorWorkDTO();
        dto.setId(entity.getId());
        dto.setWorkPercentage(entity.getWorkPercentage());
        dto.setProgress(entity.getProgress());
        dto.setProject(ProjectService.mapToDTO(entity.getProject(), false));
        dto.setSubcontractor(SubcontractorService.mapToDTO(entity.getSubcontractor(), false));
        return dto;
    }
}
