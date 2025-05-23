package ru.nsu.nextples.ms_employee.dto.position;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на обновление информации о должности")
public class PositionUpdateDTO {

    @NotBlank(message = "Не указано название должности")
    @Size(min = 2, max = 100, message = "Название должности должно содержать 2-100 символов")
    @Schema(description = "Название должности", example = "Главный Конструктор")
    private String name;
}
