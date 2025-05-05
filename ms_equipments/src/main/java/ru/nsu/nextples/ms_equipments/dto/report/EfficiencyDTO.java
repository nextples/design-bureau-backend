package ru.nsu.nextples.ms_equipments.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentDTO;
import ru.nsu.nextples.ms_equipments.model.Equipment;
import ru.nsu.nextples.ms_equipments.service.EquipmentService;

@Data
@Schema(description = "Ответ с эффективностью использования оборудования")
public class EfficiencyDTO {
    @Schema(description = "Оборудование", implementation = EquipmentDTO.class)
    private EquipmentDTO equipment;

    @Schema(description = "Количество часов использования")
    private Integer totalHoursUsed;
}
