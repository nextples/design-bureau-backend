package ru.nsu.nextples.ms_equipments.dto.equipment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.nsu.nextples.ms_equipments.dto.assignment.AssignmentDTO;
import ru.nsu.nextples.ms_equipments.dto.assignment.StatusChangeDTO;
import ru.nsu.nextples.ms_equipments.dto.maintenance.MaintenanceRecordDTO;
import ru.nsu.nextples.ms_equipments.dto.type.EquipmentTypeDTO;
import ru.nsu.nextples.ms_equipments.model.EquipmentStatus;
import ru.nsu.nextples.ms_equipments.model.EquipmentType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией об оборудовании")
public class EquipmentDTO {

    /******************
     ***    PURE    ***
     ******************/
    @Schema(description = "ID оборудования")
    private UUID id;

    @Schema(description = "Название оборудования")
    private String name;

    @Schema(description = "Статус оборудования", implementation = EquipmentStatus.class)
    private EquipmentStatus status;

    @Schema(description = "ID отдела, к которому приписано оборудование")
    private UUID currentDepartmentId;

    @Schema(description = "ID проекта, к которому приписано оборудование")
    private UUID currentProjectId;

    @Schema(description = "Тип оборудования", implementation = EquipmentType.class)
    private EquipmentTypeDTO equipmentType;

    /**********************
     ***    DETAILED    ***
     **********************/
    @Schema(description = "Серийный номер оборудования", example = "EQ-DCH4278")
    private String serialNumber;

    @Schema(description = "Дата покупки")
    private LocalDate purchaseDate;

    @Schema(description = "Является ли оборудование общим для всей организации")
    private Boolean isShared;

    @Schema(description = "История привязок оборудования к отделам", implementation = AssignmentDTO.class)
    private List<AssignmentDTO> departmentAssignments;

    @Schema(description = "История привязок оборудования к проектам", implementation = AssignmentDTO.class)
    private List<AssignmentDTO> projectAssignments;

    @Schema(description = "История изменения статуса", implementation = StatusChangeDTO.class)
    private List<StatusChangeDTO> statusChanges;

    @Schema(description = "История тех обслуживания", implementation = MaintenanceRecordDTO.class)
    private List<MaintenanceRecordDTO> maintenanceRecords;
}
