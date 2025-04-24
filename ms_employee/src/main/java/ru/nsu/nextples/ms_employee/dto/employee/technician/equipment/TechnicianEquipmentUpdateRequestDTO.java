package ru.nsu.nextples.ms_employee.dto.employee.technician.equipment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Запрос на обновление информации об оборудовании техника")
public class TechnicianEquipmentUpdateRequestDTO {

    @Schema(description = "ID сотрудника")
    private UUID employeeId;

    @Schema(description = "ID оборудования")
    private UUID equipmentId;
}
