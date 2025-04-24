package ru.nsu.nextples.ms_employee.dto.employee.engineer.specialization;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией о специализации инженеров")
public class EngineerSpecializationResponseDTO {

    @Schema(description = "ID специализации")
    private UUID id;

    @Schema(description = "Название специализации")
    private String name;

    @Schema(description = "Список ID сотрудников с этой специализацией")
    private List<UUID> employeeIds;
}
