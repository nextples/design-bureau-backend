package ru.nsu.nextples.ms_employee.dto_old.employee.technician;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией о технике")
public class TechnicianResponseDTO {
    @Schema(description = "ID техника")
    private UUID id;

    @Schema(description = "ID сотрудника")
    private UUID employeeId;
}
