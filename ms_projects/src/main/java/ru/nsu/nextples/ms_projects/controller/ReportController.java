package ru.nsu.nextples.ms_projects.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.nextples.ms_projects.dto.project.EfficiencyDTO;
import ru.nsu.nextples.ms_projects.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects/reports")
@Tag(name = "Отчеты", description = "API для управления отчетами по проектам")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/efficiency")
    @Operation(summary = "Получить эффективность проекта")
    public List<EfficiencyDTO> getProjectEfficiency() {
        return reportService.getProjectEfficiency();
    }
}
