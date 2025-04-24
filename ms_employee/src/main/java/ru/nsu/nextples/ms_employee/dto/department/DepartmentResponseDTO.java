package ru.nsu.nextples.ms_employee.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией об отделе")
public class DepartmentResponseDTO {
    @Schema(description = "ID отдела")
    private UUID id;

    @Schema(description = "Название отдела")
    private String name;

    @Schema(description = "ID руководителя отдела")
    private UUID headId;

    @Schema(description = "Список ID сотрудников отдела")
    private List<UUID> employeeIds;
}
