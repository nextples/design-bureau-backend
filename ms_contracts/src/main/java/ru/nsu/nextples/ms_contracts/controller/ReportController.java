package ru.nsu.nextples.ms_contracts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.nextples.ms_contracts.dto.contract.EfficiencyDTO;
import ru.nsu.nextples.ms_contracts.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contracts/reports")
@Tag(name = "Отчеты", description = "API для управления отчетами по договорам")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/efficiency")
    @Operation(summary = "Получить эффективность договора")
    public List<EfficiencyDTO> getContractEfficiency() {
        return reportService.getContractEfficiency();
    }
}