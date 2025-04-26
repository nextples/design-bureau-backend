package ru.nsu.nextples.ms_employee.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.nsu.nextples.ms_employee.dto.employee.EmployeeDTO;

import java.util.Set;
import java.util.UUID;

@Data
@Schema(description = "Ответ с подробной информацией об отделе")
public class DepartmentDTO {

    /*******************
     ***    PURE    ***
     *******************/
    @Schema(description = "ID отдела")
    private UUID id;

    @Schema(description = "Название отдела")
    private String name;

    @Schema(description = "Количество сотрудников")
    private Integer totalEmployees;

    @Schema(description = "Руководителя отдела")
    private EmployeeDTO head;

    @Schema(description = "Список сотрудников отдела")
    private Set<EmployeeDTO> employees;


}
