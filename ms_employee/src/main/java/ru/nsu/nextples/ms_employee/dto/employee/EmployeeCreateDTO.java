package ru.nsu.nextples.ms_employee.dto.employee;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.nsu.nextples.ms_employee.model.EmployeeType;

import java.util.Set;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание сотрудника")
public class EmployeeCreateDTO {

    @NotBlank(message = "Не указано имя сотрудника")
    @Size(min = 2, max = 50, message = "Имя должно содержать 2-50 символов")
    @Schema(description = "Имя сотрудника", requiredMode = REQUIRED)
    private String firstName;

    @NotBlank(message = "Не указана фамилия сотрудника")
    @Size(min = 2, max = 50, message = "Фамилия должна содержать 2-50 символов")
    @Schema(description = "Фамилия сотрудника", requiredMode = REQUIRED)
    private String lastName;

    @NotNull(message = "Не указан возраст сотрудника")
    @Min(value = 16, message = "Минимальный возраст 16 лет")
    @Schema(description = "Возраст сотрудника", requiredMode = REQUIRED)
    private Integer age;

    @NotNull(message = "Не указан тип сотрудника")
    @Schema(description = "Тип сотрудника", requiredMode = REQUIRED, implementation = EmployeeType.class)
    private EmployeeType employeeType;

    @NotNull(message = "Не указан ID должности сотрудника")
    @Schema(description = "ID должности сотрудника", requiredMode = REQUIRED)
    private UUID positionId;

    @NotNull(message = "Не указан ID отдела сотрудника")
    @Schema(description = "ID отдела сотрудника", requiredMode = REQUIRED)
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

    @Valid
    @Schema(description = "ID оборудования (для техников)")
    Set<@NotNull UUID> equipmentIds;
}
