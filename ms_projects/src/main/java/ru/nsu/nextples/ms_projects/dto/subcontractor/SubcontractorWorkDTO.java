package ru.nsu.nextples.ms_projects.dto.subcontractor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.nsu.nextples.ms_projects.dto.project.ProjectDTO;

import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией о работе субподрядчика")
public class SubcontractorWorkDTO {

    @Schema(description = "ID работы субподрядчика")
    private UUID id;

    @Schema(description = "Процент от общего числа работ, выделенный субподрядчику")
    private Integer workPercentage;

    @Schema(description = "Прогресс выполнения работы")
    private Integer progress;

    @Schema(description = "Проект, в котором выполняется работа")
    private ProjectDTO project;

    @Schema(description = "Субподрядчик, выполняющий работу")
    private SubcontractorDTO subcontractor;
}
