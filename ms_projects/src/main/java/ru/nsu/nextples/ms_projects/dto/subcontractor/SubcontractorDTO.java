package ru.nsu.nextples.ms_projects.dto.subcontractor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Ответ с информацией о субподрядчике")
public class SubcontractorDTO {

    /******************
     ***    PURE    ***
     ******************/
    @Schema(description = "ID субподрядчика")
    private UUID id;

    @Schema(description = "Название компании-субподрядчика")
    private String companyName;

    @Schema(description = "Email субподрядчика")
    private String email;

    @Schema(description = "Номер телефона субподрядчика")
    private String phoneNumber;

    /**********************
     ***    DETAILED    ***
     **********************/
    @Schema(description = "Работы выполняемые субподрядчиком", implementation = SubcontractorDTO.class)
    private List<SubcontractorWorkDTO> works;
}
