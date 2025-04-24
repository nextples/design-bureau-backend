package ru.nsu.nextples.ms_employee.dto.laboratory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией о лаборатории")
public class LaboratoryResponseDTO {
    @Schema(description = "ID лаборатории")
    private UUID id;

    @Schema(description = "Название лаборатории")
    private String name;

    @Schema(description = "Список ID сотрудников, работающих в лаборатории")
    private List<UUID> employeeIds;
}
