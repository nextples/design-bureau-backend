package ru.nsu.nextples.ms_employee.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание отдела")
public class DepartmentCreateDTO {

    @NotBlank(message = "Не указано название отдела")
    @Size(min = 3, max = 100, message = "Название отдела должно содержать 3-100 символов")
    @Schema(description = "Название отдела", requiredMode = REQUIRED)
    private String name;

    @Schema(description = "ID руководителя отдела")
    private UUID headId;
}
