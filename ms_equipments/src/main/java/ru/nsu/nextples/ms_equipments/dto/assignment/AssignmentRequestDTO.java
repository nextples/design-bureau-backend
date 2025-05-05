package ru.nsu.nextples.ms_equipments.dto.assignment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Запрос для назначения оборудования")
public class AssignmentRequestDTO {

    @NotNull(message = "Не указан ID проекта или отдела для назначения")
    private UUID targetId; // projectId или departmentId

    @NotBlank(message = "Не указана цель назначения")
    @Size(max = 500, message = "Цель назначения должна содержать не больше 500 символов")
    private String purpose;
}
