package ru.nsu.nextples.ms_employee.dto.employee.lab_assistant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией о лаборатории")
public class LaboratoryDTO {

    @Schema(description = "ID лаборатории")
    private UUID id;

    @Schema(description = "Название лаборатории", example = "Лаборатория аэродинамики")
    private String name;
}
