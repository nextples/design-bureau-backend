package ru.nsu.nextples.ms_employee.dto_old.employee.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на обновление информации о категории сотрудников")
public class EmployeeCategoryUpdateRequestDTO {

    @Size(min = 2, max = 50, message = "Название категории сотрудников должно содержать 2-50 символов")
    @Schema(description = "Название категории")
    private String name;

}
