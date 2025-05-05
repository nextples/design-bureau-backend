package ru.nsu.nextples.ms_equipments.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentDTO;
import ru.nsu.nextples.ms_equipments.dto.error.ErrorDTO;
import ru.nsu.nextples.ms_equipments.dto.type.EquipmentTypeCreateDTO;
import ru.nsu.nextples.ms_equipments.dto.type.EquipmentTypeDTO;
import ru.nsu.nextples.ms_equipments.dto.type.EquipmentTypeUpdateDTO;
import ru.nsu.nextples.ms_equipments.service.EquipmentTypeService;

import java.util.UUID;

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

    @PutMapping("/{id}")
    @Operation(summary = "Обновить тип оборудования")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EquipmentDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<EquipmentTypeDTO> updateEquipmentType(
            @PathVariable UUID id,
            @Valid @RequestBody EquipmentTypeUpdateDTO request
    ) {
        EquipmentTypeDTO dto = typeService.updateEquipmentType(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }


    @GetMapping
    @Operation(summary = "Получить список всех типов оборудования")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EquipmentTypeDTO.class))
                    )
            )
    })
    public ResponseEntity<Page<EquipmentTypeDTO>> getEquipmentTypes(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(typeService.getEquipmentTypes(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию о типе оборудования")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EquipmentTypeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<EquipmentTypeDTO> getEquipmentTypes(@PathVariable UUID id) {
        return ResponseEntity.ok(typeService.getEquipmentType(id));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить тип оборудования")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
    })
    public ResponseEntity<Void> deleteEquipment(@PathVariable UUID id) {
        typeService.softDeleteEquipmentType(id);
        return ResponseEntity.noContent().build();
    }
}
