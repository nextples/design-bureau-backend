package ru.nsu.nextples.ms_equipments.dto.assignment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentDTO;
import ru.nsu.nextples.ms_equipments.model.EquipmentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией о смене статуса оборудования")
public class StatusChangeDTO {

    @Schema(description = "ID записи о смене статуса")
    private UUID id;

    @Schema(description = "Краткое представление оборудования", implementation = EquipmentDTO.class)
    private EquipmentDTO equipment;

    @Schema(description = "Старый статус", implementation = EquipmentStatus.class)
    private EquipmentStatus oldStatus;

    @Schema(description = "Новый статус", implementation = EquipmentStatus.class)
    private EquipmentStatus newStatus;

    @Schema(description = "Дата изменения")
    private LocalDateTime changeDate;
}
