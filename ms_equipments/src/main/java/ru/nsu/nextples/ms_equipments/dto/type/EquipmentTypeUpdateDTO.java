package ru.nsu.nextples.ms_equipments.dto.type;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.nsu.nextples.ms_equipments.model.EquipmentCategory;

@Data
@Schema(description = "Запрос на обновление типа оборудования")
public class EquipmentTypeUpdateDTO {

    @Size(min = 2, max = 100, message = "Название должно содержать 2-100 символов")
    @Schema(description = "Название оборудования")
    private String name;

    @Size(max = 500, message = "Описание оборудования должно содержать не больше 500 символов")
    @Schema(description = "Описание оборудования")
    private String description;

    @Schema(description = "Категория оборудования", implementation = EquipmentCategory.class)
    private EquipmentCategory category;
}