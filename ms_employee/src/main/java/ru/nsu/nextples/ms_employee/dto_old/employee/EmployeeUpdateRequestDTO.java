package ru.nsu.nextples.ms_employee.dto_old.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Запрос на обновление информации о сотруднике")
public class EmployeeUpdateRequestDTO {

    @Size(min = 2, max = 50, message = "Имя должно содержать 2-50 символов")
    @Schema(description = "Имя сотрудника")
    private String firstName;

    @Size(min = 2, max = 50, message = "Фамилия должна содержать 2-50 символов")
    @Schema(description = "Фамилия сотрудника")
    private String lastName;

    @Min(value = 16, message = "Минимальный возраст 16 лет")
    @Schema(description = "Возраст сотрудника")
    private int age;

    @Schema(description = "ID категории сотрудника")
    private UUID categoryId;

    @Schema(description = "ID отдела сотрудника")
    private UUID departmentId;
}
