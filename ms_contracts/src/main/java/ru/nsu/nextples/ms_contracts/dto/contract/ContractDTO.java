package ru.nsu.nextples.ms_contracts.dto.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.nsu.nextples.ms_contracts.model.ContractStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией о договоре")
public class ContractDTO {

    /******************
     ***    PURE    ***
     ******************/
    @Schema(description = "ID договора")
    private UUID contractId;

    @Schema(description = "Название договора")
    private String name;

    @Schema(description = "Дата начала договора")
    private LocalDate startDate;

    @Schema(description = "Дата окончания договора")
    private LocalDate endDate;

    @Schema(description = "ID сотрудника, управляющего договором")
    private UUID managerId;

    /**********************
     ***    DETAILED    ***
     **********************/
    @Schema(description = "Список проектов в договоре")
    private List<UUID> projectIds;
}
