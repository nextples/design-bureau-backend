package ru.nsu.nextples.ms_employee.dto_old.employee.designer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Запрос на обновление информации о конструкторе")
public class DesignerUpdateRequestDTO {

    @Schema(description = "ID сотрудника")
    private UUID employeeId;

    @Min(value = 0, message = "Минимальное количество патентов 0")
    @Schema(description = "Количество патентов")
    private Integer patentsCount;
}
