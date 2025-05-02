package ru.nsu.nextples.ms_equipments.dto.maintenance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentDTO;
import ru.nsu.nextples.ms_equipments.model.MaintenanceStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией о записи тех обслуживания")
public class MaintenanceRecordDTO {

    @Schema(description = "ID записи тех обслуживания")
    private UUID id;

    @Schema(description = "Краткое представление оборудования", implementation = EquipmentDTO.class)
    private EquipmentDTO equipment;

    @Schema(description = "Дата начала тех обслуживания")
    private LocalDate startDate;

    @Schema(description = "Дата окончания тех обслуживания")
    private LocalDate endDate;

    @Schema(description = "Статус тех обслуживания")
    private MaintenanceStatus status;

    @Schema(description = "Описание работ")
    private String description;

    @Schema(description = "Стоимость")
    private BigDecimal cost;

    @Schema(description = "Обслуживающая компания")
    private String serviceCompany;
}
