package ru.nsu.nextples.ms_contracts.dto.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Запрос на создание договора")
public class ContractCreateDTO {

    @NotBlank(message = "Не указано название договора")
    @Schema(description = "Название договора")
    private String name;

    @NotNull(message = "Не указан ID сотрудника, управляющего договором")
    @Schema(description = "ID сотрудника, управляющего договором")
    private UUID managerId;
}
