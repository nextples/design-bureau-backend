package ru.nsu.nextples.ms_equipments.dto.equipment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(description = "Запрос на создание оборудования")
public class EquipmentCreateDTO {

    @NotBlank(message = "Не указано название оборудования")
    @Size(min = 2, max = 100, message = "Название должно содержать 2-100 символов")
    @Schema(description = "Название оборудования", requiredMode = REQUIRED)
    private String name;

    @Pattern(regexp = "^EQ-[A-Z]{3}\\d{4}$",
            message = "Серийный номер должен быть записан в формате EQ-AAA0000" +
                    "(AAA = заглавные латинские буквы, 0000 = цифры)"
    )
    @Schema(description = "Серийный номер", example = "EQ-ABC1234")
    private String serialNumber;

    @NotNull(message = "Не указан ID типа оборудования")
    @Schema(description = "ID типа оборудования", requiredMode = REQUIRED)
    private UUID equipmentTypeId;

    @Schema(description = "Дата покупки оборудования")
    private LocalDate purchaseDate;

    @Schema(description = "ID отдела, который будет владеть оборудованием")
    private UUID ownerDepartmentId;
}
