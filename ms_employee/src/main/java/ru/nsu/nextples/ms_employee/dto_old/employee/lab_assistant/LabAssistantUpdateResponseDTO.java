package ru.nsu.nextples.ms_employee.dto_old.employee.lab_assistant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Запрос на обновление информации о лаборанте")
public class LabAssistantUpdateResponseDTO {

    @Schema(description = "ID сотрудника")
    private UUID employeeId;

    @Schema(description = "ID лаборатории")
    private UUID laboratoryId;
}
