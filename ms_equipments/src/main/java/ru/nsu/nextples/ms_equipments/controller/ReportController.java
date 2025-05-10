package ru.nsu.nextples.ms_equipments.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.nextples.ms_equipments.dto.error.ErrorDTO;
import ru.nsu.nextples.ms_equipments.dto.report.EfficiencyDTO;
import ru.nsu.nextples.ms_equipments.dto.report.EquipmentDistributionDTO;
import ru.nsu.nextples.ms_equipments.dto.report.UsageDTO;
import ru.nsu.nextples.ms_equipments.service.ReportService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/equipment/reports")
@Tag(name = "Отчеты", description = "API для управления отчетами")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/usage/projects")
    @Operation(summary = "Использование оборудования проектами")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsageDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<List<UsageDTO>> getUsageByProjects(
            @RequestParam List<UUID> projectIds
    ) {
        List<UsageDTO> usage = reportService.getUsageByProjects(projectIds);
        return ResponseEntity.ok(usage);
    }

    @GetMapping("/usage/contracts/{contractId}")
    @Operation(summary = "Использование оборудования в договоре")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsageDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<List<UsageDTO>> getUsageByContract(
            @PathVariable UUID contractId
    ) {
        List<UsageDTO> usage = reportService.getUsageByContract(contractId);
        return ResponseEntity.ok(usage);
    }


    @GetMapping("/distribution")
    @Operation(summary = "Получить распределение оборудования на указанную дату")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EquipmentDistributionDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<List<EquipmentDistributionDTO>> getEquipmentDistribution(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        List<EquipmentDistributionDTO> distribution = reportService.getEquipmentDistribution(targetDate);
        return ResponseEntity.ok(distribution);
    }


    @GetMapping("/{id}/efficiency")
    @Operation(summary = "Эффективность оборудования")
    public ResponseEntity<EfficiencyDTO> getEfficiency(@PathVariable UUID id) {
        return ResponseEntity.ok(reportService.getEfficiency(id));
    }
}
