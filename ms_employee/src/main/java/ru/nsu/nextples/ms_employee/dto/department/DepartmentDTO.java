package ru.nsu.nextples.ms_employee.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.nsu.nextples.ms_employee.dto.employee.EmployeeDTO;

import java.util.Set;
import java.util.UUID;

@Data
@Schema(description = "Ответ с подробной информацией об отделе")
public class DepartmentDTO {

    /******************
     ***    PURE    ***
     ******************/
    @Schema(description = "ID отдела")
    private UUID id;

    @Schema(description = "Название отдела", example = "Отдел машиностроения")
    private String name;

    @Schema(description = "Количество сотрудников", example = "34")
    private Integer totalEmployees;

    @Schema(description = "Руководителя отдела", implementation = EmployeeDTO.class)
    private EmployeeDTO head;

    /**********************
     ***    DETAILED    ***
     **********************/
    @Schema(description = "Список сотрудников отдела")
    private Set<EmployeeDTO> employees;
}
