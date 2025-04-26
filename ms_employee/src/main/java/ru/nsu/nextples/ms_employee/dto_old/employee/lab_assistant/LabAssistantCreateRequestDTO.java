package ru.nsu.nextples.ms_employee.dto_old.employee.lab_assistant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание лаборанта")
public class LabAssistantCreateRequestDTO {

    @NotNull(message = "ID сотрудника не указан")
    @Schema(description = "ID сотрудника", requiredMode = REQUIRED)
    private UUID employeeId;

    @NotNull(message = "ID лаборатории не указан")
    @Schema(description = "ID лаборатории", requiredMode = REQUIRED)
    private UUID laboratoryId;
}
