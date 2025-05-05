package ru.nsu.nextples.ms_equipments.dto.equipment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Schema(description = "Ответ с распределением оборудования на определенную дату")
public class EquipmentDistributionDTO {

    @Schema(description = "ID оборудования")
    private UUID equipmentId;

    @Schema(description = "Название оборудования")
    private String equipmentName;

    @Schema(description = "ID отдела")
    private UUID departmentId;

    @Schema(description = "ID проекта")
    private UUID projectId;

    @Schema(description = "Дата назначения")
    private LocalDate startDate;

    @Schema(description = "Дата возврата")
    private LocalDate endDate;

    @Schema(description = "Тип назначения: PROJECT или DEPARTMENT")
    private String assignmentType; // "PROJECT" или "DEPARTMENT"
}