package ru.nsu.nextples.ms_employee.dto_old.employee.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание категории сотрудников")
public class EmployeeCategoryCreateRequestDTO {

    @NotBlank(message = "Не указано название категории")
    @Size(min = 2, max = 50, message = "Название категории сотрудников должно содержать 2-50 символов")
    @Schema(description = "Название категории", requiredMode = REQUIRED)
    private String name;
}
