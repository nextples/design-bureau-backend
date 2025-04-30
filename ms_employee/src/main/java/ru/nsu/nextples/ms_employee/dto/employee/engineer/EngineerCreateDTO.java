package ru.nsu.nextples.ms_employee.dto.employee.engineer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание инженера")
public class EngineerCreateDTO {

    @NotNull(message = "ID сотрудника не указан")
    @Schema(description = "ID сотрудника", requiredMode = REQUIRED)
    private UUID employeeId;

    @NotNull(message = "ID специализации не указан")
    @Schema(description = "ID специализации инженера", requiredMode = REQUIRED)
    private UUID specializationId;
}
