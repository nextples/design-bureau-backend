package ru.nsu.nextples.ms_employee.dto_old.employee.lab_assistant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией о лаборанте")
public class LabAssistantResponseDTO {
    @Schema(description = "ID лаборанта")
    private UUID id;

    @Schema(description = "ID сотрудника")
    private UUID employeeId;

    @Schema(description = "ID лаборатории")
    private UUID laboratoryId;
}
