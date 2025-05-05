package ru.nsu.nextples.ms_equipments.dto.assignment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на возвращение оборудования")
public class ReturnRequestDTO {

    @NotNull(message = "Не указано количество часов использования")
    @Min(value = 0, message = "Минимальное число часов использования 0")
    @Schema(description = "ЧКоличество часов использования оборудования", requiredMode = REQUIRED)
    private Integer hoursUsed;
}
