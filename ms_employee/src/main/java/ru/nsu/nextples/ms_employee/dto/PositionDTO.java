package ru.nsu.nextples.ms_employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.nsu.nextples.ms_employee.model.EmployeeType;

import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией о должности")
public class PositionDTO {

    @Schema(description = "ID должности")
    private UUID id;

    @Schema(description = "Тип сотрудника", implementation = EmployeeType.class)
    private EmployeeType employeeType;

    @Schema(description = "Название должности", example = "Главный Конструктор")
    private String name;
}