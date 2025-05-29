package ru.nsu.nextples.ms_projects.dto.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.nsu.nextples.ms_projects.dto.subcontractor.SubcontractorWorkDTO;
import ru.nsu.nextples.ms_projects.model.ProjectStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией о проекте")
public class ProjectDTO {

    /******************
     ***    PURE    ***
     ******************/
    @Schema(description = "ID проекта")
    private UUID id;

    @Schema(description = "Название проекта")
    private String name;

    @Schema(description = "Описание проекта")
    private String description;

    @Schema(description = "Статус проекта")
    private ProjectStatus status;

    @Schema(description = "ID отдела, ответственного за проект")
    private UUID responsibleDepartmentId;

    @Schema(description = "Дата начала проекта")
    private LocalDate startDate;

    @Schema(description = "Дата окончания проекта")
    private LocalDate endDate;

    @Schema(description = "Цена проекта")
    private BigDecimal cost;

    @Schema(description = "Внутренний прогресс выполнения проекта")
    private Integer internalProgress;

    @Schema(description = "Общий прогресс выполнения проекта")
    private Integer totalProgress;

    /**********************
     ***    DETAILED    ***
     **********************/
    @Schema(description = "Список ID сотрудников, работающих над проектом")
    private List<UUID> employeeIds;

    @Schema(description = "Список ID оборудования, используемого в проекте")
    private List<UUID> equipmentIds;

    @Schema(description = "Список ID контрактов, в которые включен проект")
    private List<UUID> contractIds;

    @Schema(description = "Список субподрядных работ в проекте", implementation = SubcontractorWorkDTO.class)
    private List<SubcontractorWorkDTO> subcontractorWorks;
}
