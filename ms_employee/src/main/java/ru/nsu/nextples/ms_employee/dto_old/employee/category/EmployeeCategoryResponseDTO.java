package ru.nsu.nextples.ms_employee.dto_old.employee.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Ответ с данными категории сотрудника")
public class EmployeeCategoryResponseDTO {
    @Schema(description = "ID категории")
    private UUID id;

    @Schema(description = "Название категории")
    private String name;

    @Schema(description = "Список ID сотрудников в категории")
    private List<UUID> employeeIds;
}
