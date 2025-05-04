package ru.nsu.nextples.ms_equipments.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.nextples.ms_equipments.dto.error.ErrorDTO;
import ru.nsu.nextples.ms_equipments.dto.type.EquipmentTypeCreateDTO;
import ru.nsu.nextples.ms_equipments.dto.type.EquipmentTypeDTO;
import ru.nsu.nextples.ms_equipments.service.EquipmentTypeService;

@RestController
@RequestMapping("/api/v1/equipment-types")
@Tag(name = "Типы оборудования", description = "API для управления типами оборудования")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EquipmentTypeController {

    private final EquipmentTypeService typeService;

    @PostMapping
    @Operation(summary = "Создать тип оборудования")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EquipmentTypeDTO.class)
                    )
            ),
    })
    public ResponseEntity<EquipmentTypeDTO> createEquipment(@Valid @RequestBody EquipmentTypeCreateDTO request) {
        EquipmentTypeDTO dto = typeService.createEquipmentType(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
