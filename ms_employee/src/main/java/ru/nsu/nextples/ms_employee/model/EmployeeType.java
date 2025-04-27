package ru.nsu.nextples.ms_employee.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Тип сотрудника")
public enum EmployeeType {
    @Schema DESIGNER,
    @Schema ENGINEER,
    @Schema TECHNICIAN,
    @Schema LAB_ASSISTANT
}
