package ru.nsu.nextples.ms_projects.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.nextples.ms_projects.dto.project.EfficiencyDTO;
import ru.nsu.nextples.ms_projects.repository.ProjectRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportService {

    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public List<EfficiencyDTO> getProjectEfficiency() {
        return projectRepository.findAll().stream()
                .map(project -> {
                    BigDecimal costPerDay = project.getCost()
                            .divide(BigDecimal.valueOf(ChronoUnit.DAYS.between(
                                    project.getStartDate(),
                                    project.getEndDate()
                            )), 2, RoundingMode.HALF_UP);

                    EfficiencyDTO dto = new EfficiencyDTO();
                    dto.setProject(ProjectService.mapToDTO(project, false));
                    dto.setCostPerDay(costPerDay);

                    return dto;
                })
                .collect(Collectors.toList());
    }
}
