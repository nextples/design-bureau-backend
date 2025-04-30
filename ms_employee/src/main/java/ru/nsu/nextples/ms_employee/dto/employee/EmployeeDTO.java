package ru.nsu.nextples.ms_employee.dto.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.nsu.nextples.ms_employee.dto.employee.engineer.EngineerSpecializationDTO;
import ru.nsu.nextples.ms_employee.dto.department.DepartmentDTO;
import ru.nsu.nextples.ms_employee.dto.position.PositionDTO;
import ru.nsu.nextples.ms_employee.dto.employee.lab_assistant.LaboratoryDTO;
import ru.nsu.nextples.ms_employee.model.EmployeeType;

import java.util.Set;
import java.util.UUID;

@Data
@Schema(description = "Ответ с детальной информацией о сотруднике")
public class EmployeeDTO {

    /******************
     ***    PURE    ***
     ******************/
    @Schema(description = "ID сотрудника")
    private UUID id;

    @Schema(description = "Имя сотрудника", example = "Петр")
    private String firstName;

    @Schema(description = "Фамилия сотрудника", example = "Петров")
    private String lastName;

    @Schema(description = "Должность сотрудника", implementation = PositionDTO.class)
    private PositionDTO position;

    @Schema(description = "Тип сотрудника", implementation = EmployeeType.class)
    private EmployeeType employeeType;

    /**********************
     ***    DETAILED    ***
     **********************/
    @Schema(description = "Возраст сотрудника", example = "24")
    private Integer age;

    @Schema(description = "Название отдела сотрудника", implementation = DepartmentDTO.class)
    private DepartmentDTO department;

    @Schema(description = "Название отдела, возглавляемого сотрудником", implementation = DepartmentDTO.class)
    private DepartmentDTO managedDepartment;

    @Schema(description = "Список специализаций инженера (если применимо)", implementation = EngineerSpecializationDTO.class)
    private Set<EngineerSpecializationDTO> specializations;

    @Schema(description = "Количество патентов конструктора (если применимо)", example = "4")
    private Integer patentsCount;

    @Schema(description = "Лаборатория лаборанта (если применимо)", implementation = LaboratoryDTO.class)
    private LaboratoryDTO laboratory;

    @Schema(description = "Список оборудования, обслуживаемого техником (если применимо)")
    private Set<UUID> assignedEquipment;
}
