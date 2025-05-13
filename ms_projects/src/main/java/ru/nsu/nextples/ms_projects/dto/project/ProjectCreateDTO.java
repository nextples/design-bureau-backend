package ru.nsu.nextples.ms_projects.dto.project;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание проекта")
public class ProjectCreateDTO {

    @NotBlank(message = "Не указано название проекта")
    @Size(min = 1, max = 100, message = "Название должно содержать 1-100 символов")
    @Schema(description = "Название проекта", requiredMode = REQUIRED)
    private String name;

    @Size(max = 500, message = "Описание проекта должно быть не больше 500 символов")
    @Schema(description = "Описание проекта")
    private String description;

    @NotNull(message = "ID ответственно отдела не указан")
    @Schema(description = "ID отдела, ответственного за проект", requiredMode = REQUIRED)
    private UUID responsibleDepartmentId;

    @NotNull(message = "Не указана стоимость проекта")
    @Schema(description = "Стоимость проекта")
    private BigDecimal cost;
}
