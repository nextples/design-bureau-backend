package ru.nsu.nextples.ms_equipments.dto.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.nsu.nextples.ms_equipments.model.EquipmentCategory;

import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией о типе оборудования")
public class EquipmentTypeDTO {

    @Schema(description = "ID типа оборудования")
    private UUID id;

    @Schema(description = "Название оборудования")
    private String name;

    @Schema(description = "Описание оборудования")
    private String description;

    @Schema(description = "Категория оборудования", implementation = EquipmentCategory.class)
    private EquipmentCategory category;
}
