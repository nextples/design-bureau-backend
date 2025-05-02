package ru.nsu.nextples.ms_equipments.dto.assignment;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentDTO;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией о привязке оборудования к отделу/проекту")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssignmentDTO {

    @Schema(description = "ID привязки")
    private UUID id;

    @Schema(description = "Краткое представление оборудования", implementation = EquipmentDTO.class)
    private EquipmentDTO equipment;

    @Schema(description = "ID отдела")
    private UUID departmentId;

    @Schema(description = "ID проекта")
    private UUID projectId;

    @Schema(description = "Дата привязки")
    private LocalDate startDate;

    @Schema(description = "Дата возврата")
    private LocalDate endDate;

    @Schema(description = "Часов использования")
    private Integer hoursUsed;

    @Schema(description = "Цель привязки")
    private String purpose;
}
