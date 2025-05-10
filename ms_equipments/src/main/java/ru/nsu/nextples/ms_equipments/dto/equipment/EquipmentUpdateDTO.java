package ru.nsu.nextples.ms_equipments.dto.equipment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Schema(description = "Запрос на обновление оборудования")
public class EquipmentUpdateDTO {

    @Size(min = 2, max = 100, message = "Название должно содержать 2-100 символов")
    @Schema(description = "Название оборудования")
    private String name;

    @Pattern(regexp = "^EQ-[A-Z]{3}\\d{4}$",
            message = "Серийный номер должен быть записан в формате EQ-AAA0000" +
                    "(AAA = заглавные латинские буквы, 0000 = цифры)"
    )
    @Schema(description = "Серийный номер", example = "EQ-ABC1234")
    private String serialNumber;

    @Schema(description = "ID типа оборудования")
    private UUID equipmentTypeId;

    @Schema(description = "Дата покупки оборудования")
    private LocalDate purchaseDate;

    @Schema(description = "ID отдела-владельца")
    private UUID ownerDepartmentId;
}