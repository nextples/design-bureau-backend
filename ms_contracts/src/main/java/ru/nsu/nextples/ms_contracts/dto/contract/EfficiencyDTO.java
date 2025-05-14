package ru.nsu.nextples.ms_contracts.dto.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Ответ на запрос об эффективности договора")
public class EfficiencyDTO {

    @Schema(description = "Договор", implementation = ContractDTO.class)
    private ContractDTO contract;

    @Schema(description = "Цена договора в день")
    private BigDecimal costPerDay;
}
