package ru.nsu.nextples.ms_employee.dto.employee.technician;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание?изменение техника")
public class TechnicianRequestDTO {
    @NotNull(message = "ID сотрудника не указан")
    @Schema(description = "ID сотрудника", requiredMode = REQUIRED)
    private UUID employeeId;
}
