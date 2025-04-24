package ru.nsu.nextples.ms_employee.dto.employee.technician.equipment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание оборудования техника")
public class TechnicianEquipmentCreateRequestDTO {
    @NotNull(message = "ID сотрудника не указан")
    @Schema(description = "ID сотрудника", requiredMode = REQUIRED)
    private UUID employeeId;

    @NotNull(message = "ID оборудования не указан")
    @Schema(description = "ID оборудования", requiredMode = REQUIRED)
    private UUID equipmentId;
}
