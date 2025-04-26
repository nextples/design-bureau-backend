package ru.nsu.nextples.ms_employee.dto.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.nsu.nextples.ms_employee.dto.EngineerSpecializationDTO;
import ru.nsu.nextples.ms_employee.dto.department.DepartmentDTO;
import ru.nsu.nextples.ms_employee.dto.EmployeePositionDTO;
import ru.nsu.nextples.ms_employee.dto.LaboratoryDTO;

import java.util.Set;
import java.util.UUID;

@Data
@Schema(description = "Ответ с детальной информацией о сотруднике")
public class EmployeeDTO {

    /*******************
     ***    PURE    ***
     *******************/
    @Schema(description = "ID сотрудника")
    private UUID id;

    @Schema(description = "Имя сотрудника")
    private String firstName;

    @Schema(description = "Фамилия сотрудника")
    private String lastName;

    @Schema(description = "Должность сотрудника", implementation = EmployeePositionDTO.class)
    private EmployeePositionDTO position;


    /**********************
     ***    DETAILED    ***
     **********************/
    @Schema(description = "Возраст сотрудника")
    private Integer age;

    @Schema(description = "Название отдела сотрудника", implementation = DepartmentDTO.class)
    private DepartmentDTO department;

    @Schema(description = "Название отдела, возглавляемого сотрудником", implementation = DepartmentDTO.class)
    private DepartmentDTO managedDepartment;

    @Schema(description = "Список специализаций инженера (если применимо)")
    private Set<EngineerSpecializationDTO> specializations;

    @Schema(description = "Количество патентов конструктора (если применимо)")
    private Integer patentsCount;

    @Schema(description = "Лаборатория лаборанта (если применимо)", implementation = LaboratoryDTO.class)
    private LaboratoryDTO laboratory;

    @Schema(description = "Список оборудования, обслуживаемого техником (если применимо)")
    private Set<UUID> assignedEquipment;
}
