package ru.nsu.nextples.ms_projects.dto.project;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Ответ на запрос об эффективности проекта")
public class EfficiencyDTO {

    @Schema(description = "Проект", implementation = ProjectDTO.class)
    ProjectDTO project;

    @Schema(description = "Цена проекта в день")
    BigDecimal costPerDay;
}
