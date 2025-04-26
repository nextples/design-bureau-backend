package ru.nsu.nextples.ms_employee.dto_old.employee.designer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Ответ с данными о конструкторе")
public class DesignerResponseDTO {

    @Schema(description = "ID конструктора")
    private UUID id;

    @Schema(description = "ID сотрудника")
    private UUID employeeId;

    @Schema(description = "Количество патентов")
    private Integer patentsCount;
}
