package ru.nsu.nextples.ms_employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией о специализации инженеров")
public class EngineerSpecializationDTO {

    @Schema(description = "ID специализации")
    private UUID id;

    @Schema(description = "Название специализации", example = "Инженер-механик")
    private String name;
}
