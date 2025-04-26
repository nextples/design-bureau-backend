package ru.nsu.nextples.ms_employee.dto_old.employee.engineer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией об инженере")
public class EngineerResponseDTO {
    @Schema(description = "ID инженера")
    private UUID id;

    @Schema(description = "ID сотрудника")
    private UUID employeeId;

    @Schema(description = "ID специализации инженера")
    private UUID specializationId;
}
