package ru.nsu.nextples.ms_employee.dto.laboratory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание/обновление лаборатории")
public class LaboratoryCreateUpdateDTO {

    @NotBlank(message = "Не указано название лаборатории")
    @Size(min = 2, max = 100, message = "Название лаборатории должно содержать 2-100 символов")
    @Schema(description = "Название лаборатории", example = "Лаборатория аэродинамики")
    private String name;
}