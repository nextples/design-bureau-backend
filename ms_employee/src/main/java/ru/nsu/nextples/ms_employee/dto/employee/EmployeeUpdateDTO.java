package ru.nsu.nextples.ms_employee.dto.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.nsu.nextples.ms_employee.model.EmployeeType;

import java.util.Set;
import java.util.UUID;

@Data
@Schema(description = "Запрос на обновление информации о сотруднике")
public class EmployeeUpdateDTO {

    @Size(min = 2, max = 50, message = "Имя должно содержать 2-50 символов")
    @Schema(description = "Имя сотрудника")
    private String firstName;

    @Size(min = 2, max = 50, message = "Фамилия должна содержать 2-50 символов")
    @Schema(description = "Фамилия сотрудника")
    private String lastName;

    @Min(value = 16, message = "Минимальный возраст 16 лет")
    @Schema(description = "Возраст сотрудника")
    private Integer age;

    @Schema(description = "Тип сотрудника", implementation = EmployeeType.class)
    private EmployeeType employeeType;

    @Schema(description = "ID должности сотрудника")
    private UUID positionId;

    @Schema(description = "ID отдела сотрудника")
    private UUID departmentId;

    /**********************
     ***    OPTIONAL    ***
     **********************/
    @Min(value = 0, message = "Минимальное количество патентов 0")
    @Schema(description = "Количество патентов (для конструкторов)")
    private Integer patentsCount;

    @Schema(description = "ID специализаций (для инженеров)")
    Set<UUID> specializationIds;

    @Schema(description = "ID лаборатории (для лаборантов)")
    UUID laboratoryId;

    @Schema(description = "ID оборудования (для техников)")
    Set<UUID> equipmentIds;
}
