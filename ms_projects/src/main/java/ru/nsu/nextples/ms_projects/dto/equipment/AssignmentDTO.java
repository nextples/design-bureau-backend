package ru.nsu.nextples.ms_projects.dto.equipment;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией о назначении оборудования отделу/проекту")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssignmentDTO {

    @Schema(description = "ID назначения")
    private UUID id;

    @Schema(description = "ID оборудования")
    private UUID equipmentId;

    @Schema(description = "ID отдела")
    private UUID departmentId;

    @Schema(description = "ID проекта")
    private UUID projectId;

    @Schema(description = "Дата назначения")
    private LocalDate startDate;

    @Schema(description = "Дата назначения")
    private LocalDate endDate;

    @Schema(description = "Часов использования")
    private Integer hoursUsed;

    @Schema(description = "Цель назначения")
    private String purpose;
}
