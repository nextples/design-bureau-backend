package ru.nsu.nextples.ms_equipments.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentDTO;

import java.util.*;

@Data
@Schema(description = "Ответ содержащий информацию об использовании оборудования")
public class UsageDTO {

    @Schema(description = "ID проекта")
    private UUID projectId;

    @Schema(description = "Список использованного оборудования и время использования")
    private Map<EquipmentDTO, Integer> equipmentStats = new HashMap<>();
}