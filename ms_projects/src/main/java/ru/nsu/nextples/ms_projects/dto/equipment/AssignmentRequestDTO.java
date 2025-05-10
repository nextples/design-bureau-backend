package ru.nsu.nextples.ms_projects.dto.equipment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Запрос для назначения оборудования на проект")
public class AssignmentRequestDTO {

    @NotNull(message = "Не указан ID проекта для назначения")
    private UUID projectId;

    @NotNull(message = "Не указан ID отдела, ответственного за проект")
    private UUID responsibleDepartmentId;

    @NotBlank(message = "Не указана цель назначения")
    @Size(max = 500, message = "Цель назначения должна содержать не больше 500 символов")
    private String purpose;
}
