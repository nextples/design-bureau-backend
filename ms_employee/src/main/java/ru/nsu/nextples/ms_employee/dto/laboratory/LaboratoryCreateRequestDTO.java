package ru.nsu.nextples.ms_employee.dto.laboratory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание лаборатории")
public class LaboratoryCreateRequestDTO {

    @NotBlank(message = "Название лаборатории не указано")
    @Size(min = 2, max = 50, message = "Название лаборатории должно содержать 2-50 символов")
    @Schema(description = "Название лаборатории", requiredMode = REQUIRED)
    private String name;
}
