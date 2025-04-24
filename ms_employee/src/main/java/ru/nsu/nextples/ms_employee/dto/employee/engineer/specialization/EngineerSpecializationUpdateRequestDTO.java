package ru.nsu.nextples.ms_employee.dto.employee.engineer.specialization;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на обновление информации о специализации инженеров")
public class EngineerSpecializationUpdateRequestDTO {

    @Size(min  = 2, max = 100, message = "Название специализации должно содержать 2-100 символов")
    @Schema(description = "Название специализации")
    private String name;
}
