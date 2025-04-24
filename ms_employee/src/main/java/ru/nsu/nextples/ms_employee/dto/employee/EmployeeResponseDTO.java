package ru.nsu.nextples.ms_employee.dto.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Ответ с данными сотрудника")
public class EmployeeResponseDTO {

    @Schema(description = "ID сотрудника")
    private UUID id;

    @Schema(description = "Имя сотрудника")
    private String firstName;

    @Schema(description = "Фамилия сотрудника")
    private String lastName;

    @Schema(description = "ID категории сотрудника")
    private UUID categoryId;

    @Schema(description = "Возраст сотрудника")
    private Integer age;

    @Schema(description = "ID отдела сотрудника")
    private UUID departmentId;

    @Schema(description = "ID отдела, где сотрудник является руководителем")
    private UUID departmentIdAsHead;

    @Schema(description = "ID конструктора (если применимо)")
    private UUID designerId;

    @Schema(description = "ID техника (если применимо)")
    private UUID technicianId;

    @Schema(description = "Список ID специализаций инженера (если применимо)")
    private List<UUID> engineerSpecializationIds;

    @Schema(description = "Список ID лабораторий (если применимо)")
    private List<UUID> laboratoryIds;
}
