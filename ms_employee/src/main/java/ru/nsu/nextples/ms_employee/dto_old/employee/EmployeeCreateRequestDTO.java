package ru.nsu.nextples.ms_employee.dto_old.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание сотрудника")
public class EmployeeCreateRequestDTO {

    @NotBlank(message = "Не указано имя сотрудника")
    @Size(min = 2, max = 50, message = "Имя должно содержать 2-50 символов")
    @Schema(description = "Имя сотрудника", requiredMode = REQUIRED)
    private String firstName;

    @NotBlank(message = "Не указана фамилия сотрудника")
    @Size(min = 2, max = 50, message = "Фамилия должна содержать 2-50 символов")
    @Schema(description = "Фамилия сотрудника", requiredMode = REQUIRED)
    private String lastName;

    @NotNull(message = "Не указан возраст сотрудника")
    @Min(value = 16, message = "Минимальный возраст 16 лет")
    @Schema(description = "Возраст сотрудника", requiredMode = REQUIRED)
    private int age;

    @NotBlank(message = "Не указан ID категории сотрудника")
    @Schema(description = "ID категории сотрудника", requiredMode = REQUIRED)
    private UUID categoryId;

    @NotBlank(message = "Не указан ID отдела сотрудника")
    @Schema(description = "ID отдела сотрудника", requiredMode = REQUIRED)
    private UUID departmentId;
}
