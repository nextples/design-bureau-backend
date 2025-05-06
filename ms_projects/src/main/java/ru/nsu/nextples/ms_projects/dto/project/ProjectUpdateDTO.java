package ru.nsu.nextples.ms_projects.dto.project;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на обновление проекта")
public class ProjectUpdateDTO {

    @Size(min = 1, max = 100, message = "Название должно содержать 1-100 символов")
    @Schema(description = "Название проекта")
    private String name;

    @Size(max = 500, message = "Описание проекта должно быть не больше 500 символов")
    @Schema(description = "Описание проекта")
    private String description;
}
