package ru.nsu.nextples.ms_employee.dto.engineer_specialization;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание/обновление специализации инженера")
public class EngineerSpecializationCreateUpdateDTO {

    @NotNull(message = "Не указано название специализации")
    @Size(min = 2, max = 100, message = "Название специализации должно содержать 2-100 символов")
    @Schema(description = "Название специализации", example = "Двигателестроение", requiredMode = REQUIRED)
    private String name;
}
