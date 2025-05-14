package ru.nsu.nextples.ms_projects.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.nextples.ms_projects.dto.subcontractor.SubcontractorWorkDTO;
import ru.nsu.nextples.ms_projects.service.SubcontractorWorkService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/subcontractors/works")
@Tag(name = "Субподрядные работы", description = "API для управления субподрядными работами")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubcontractorWorkController {

    private final SubcontractorWorkService workService;

    @PostMapping("/assign")
    @Operation(summary = "Назначить работу субподрядчику")
    public ResponseEntity<SubcontractorWorkDTO> assignWorkToSubcontractor(@RequestParam(name = "project") UUID projectId,
                                                                          @RequestParam(name = "subcontractor") UUID subcontractor,
                                                                          @RequestParam(name = "percentage") int percentage) {
        SubcontractorWorkDTO work = workService.assignWorkToSubcontractor(projectId, subcontractor, percentage);
        return ResponseEntity.ok(work);
    }

    @PatchMapping("/{workId}/progress")
    @Operation(description = "Обновить прогресс субподрядных работ")
    public ResponseEntity<SubcontractorWorkDTO> updateSubcontractorProgress(@PathVariable UUID workId,
                                                                            @Min(value = 0, message = "Минимальное значение прогресса 0%")
                                                                            @Max(value = 100, message = "Максимальное значение прогресса 100%")
                                                                            @RequestParam(name = "val") Integer newValue
    ) {
        SubcontractorWorkDTO work = workService.updateSubcontractorProgress(workId, newValue);
        return ResponseEntity.ok(work);
    }

    @GetMapping("/costs")
    @Operation(summary = "Получить все субподрядные работы с их стоимостью")
    public ResponseEntity<Map<SubcontractorWorkDTO, BigDecimal>> getCosts() {
        Map <SubcontractorWorkDTO, BigDecimal> costs = workService.getAllSubcontractedWorksWithCost();
        return ResponseEntity.ok(costs);
    }
}
