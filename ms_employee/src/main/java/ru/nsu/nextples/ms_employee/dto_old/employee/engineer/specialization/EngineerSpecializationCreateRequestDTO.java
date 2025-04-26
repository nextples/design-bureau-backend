package ru.nsu.nextples.ms_employee.dto_old.employee.engineer.specialization;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание специализации инженеров")
public class EngineerSpecializationCreateRequestDTO {

    @NotBlank(message = "Не указано название специализации")
    @Size(min  = 2, max = 100, message = "Название специализации должно содержать 2-100 символов")
    @Schema(description = "Название специализации", requiredMode = REQUIRED)
    private String name;
}
