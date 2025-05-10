package ru.nsu.nextples.ms_projects.dto.equipment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Запрос на добавление оборудования в проект")
public class AddEquipmentRequestDTO {

    @Size(min = 1)
    private List<UUID> equipmentIds;

    @NotBlank(message = "Не указана цель назначения")
    @Size(max = 500, message = "Цель назначения должна содержать не больше 500 символов")
    private String purpose;
}
