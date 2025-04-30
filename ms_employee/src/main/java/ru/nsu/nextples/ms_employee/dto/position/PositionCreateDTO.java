package ru.nsu.nextples.ms_employee.dto.position;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.nsu.nextples.ms_employee.model.EmployeeType;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание должности")
public class PositionCreateDTO {

    @NotNull(message = "Не указан тип сотрудника")
    @Schema(description = "Тип сотрудника", implementation = EmployeeType.class, requiredMode = REQUIRED)
    private EmployeeType employeeType;

    @NotNull(message = "Не указано название должности")
    @Size(min = 2, max = 100, message = "Название должности должно содержать 2-100 символов")
    @Schema(description = "Название должности", example = "Главный Конструктор", requiredMode = REQUIRED)
    private String name;
}
