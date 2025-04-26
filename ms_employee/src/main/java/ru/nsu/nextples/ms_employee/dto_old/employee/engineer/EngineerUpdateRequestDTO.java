package ru.nsu.nextples.ms_employee.dto_old.employee.engineer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Запрос на обновление информации об инженере")
public class EngineerUpdateRequestDTO {

    @Schema(description = "ID сотрудника")
    private UUID employeeId;

    @Schema(description = "ID специализации инженера")
    private UUID specializationId;
}
