package ru.nsu.nextples.ms_employee.dto.employee.designer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание конструктора")
public class DesignerCreateRequestDTO {

    @NotNull(message = "ID сотрудника не указан")
    @Schema(description = "ID сотрудника", requiredMode = REQUIRED)
    private UUID employeeId;

    @NotNull(message = "Количество патентов не указано")
    @Min(value = 0, message = "Минимальное количество патентов 0")
    @Schema(description = "Количество патентов", requiredMode = REQUIRED)
    private Integer patentsCount;
}
