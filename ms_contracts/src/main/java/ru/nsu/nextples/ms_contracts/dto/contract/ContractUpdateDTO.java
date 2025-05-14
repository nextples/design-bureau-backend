package ru.nsu.nextples.ms_contracts.dto.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Запрос на обновление договора")
public class ContractUpdateDTO {

    @Schema(description = "Название договора")
    private String name;

    @Schema(description = "ID сотрудника, управляющего договором")
    private UUID managerId;
}
