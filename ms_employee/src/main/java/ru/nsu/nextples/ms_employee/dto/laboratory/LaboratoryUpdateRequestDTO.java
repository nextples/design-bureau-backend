package ru.nsu.nextples.ms_employee.dto.laboratory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на обновление информации о лаборатории")
public class LaboratoryUpdateRequestDTO {

    @Size(min = 2, max = 50, message = "Название лаборатории должно содержать 2-50 символов")
    @Schema(description = "Название лаборатории")
    private String name;
}
