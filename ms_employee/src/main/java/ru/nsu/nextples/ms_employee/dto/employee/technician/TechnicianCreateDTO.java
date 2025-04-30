package ru.nsu.nextples.ms_employee.dto.employee.technician;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание техника")
public class TechnicianCreateDTO {

    @NotNull(message = "ID сотрудника не указан")
    @Schema(description = "ID сотрудника", requiredMode = REQUIRED)
    private UUID employeeId;

    @NotEmpty(message = "Список идентификаторов оборудования не указан")
    @Schema(description = "Список идентификаторов оборудования", requiredMode = REQUIRED)
    private Set<UUID> equipmentIds;
}
