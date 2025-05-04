package ru.nsu.nextples.ms_equipments.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentCreateDTO;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentDTO;
import ru.nsu.nextples.ms_equipments.dto.equipment.EquipmentUpdateDTO;
import ru.nsu.nextples.ms_equipments.dto.error.ErrorDTO;
import ru.nsu.nextples.ms_equipments.model.EquipmentStatus;
import ru.nsu.nextples.ms_equipments.service.EquipmentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/equipment")
@Tag(name = "Оборудование", description = "API для управления оборудованием")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping
    @Operation(summary = "Создать оборудование")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EquipmentDTO.class)
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
    public ResponseEntity<EquipmentDTO> createEquipment(@Valid @RequestBody EquipmentCreateDTO request) {
        EquipmentDTO dto = equipmentService.createEquipment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Обновить оборудование")
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
    public ResponseEntity<EquipmentDTO> updateEquipment(
            @PathVariable UUID id,
            @Valid @RequestBody EquipmentUpdateDTO request
    ) {
        EquipmentDTO dto = equipmentService.updateEquipment(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }


    @GetMapping
    @Operation(summary = "Получить список всего оборудования")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EquipmentDTO.class))
                    )
            )
    })
    public ResponseEntity<Page<EquipmentDTO>> getEquipments(
            @Parameter(description = "Фильтр по названию")
            @RequestParam(required = false) String name,
            @Parameter(description = "Фильтр по статусу")
            @RequestParam(required = false) EquipmentStatus status,
            @Parameter(description = "Фильтр по отделу")
            @RequestParam(required = false) UUID departmentId,
            @Parameter(description = "Фильтр по проекту")
            @RequestParam(required = false) UUID projectId,
            @Parameter(description = "Фильтр по типу")
            @RequestParam(required = false) UUID typeId,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(
                equipmentService.getEquipments(name, status, departmentId, projectId, typeId, pageable)
        );
    }


    @GetMapping("/{id}")
    @Operation(summary = "Получить детальную информацию об оборудовании")
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
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<EquipmentDTO> getEquipmentDetails(@PathVariable UUID id) {
        return ResponseEntity.ok(equipmentService.getEquipmentDetails(id));
    }


    @PutMapping("/exists")
    @Operation(summary = "Проверить существование оборудования")
    public ResponseEntity<Map<UUID, Boolean>> checkEquipmentExists(@RequestBody List<UUID> equipmentIds) {
        Map<UUID, Boolean> map = new HashMap<>();
        for (UUID id : equipmentIds) {
            if (equipmentService.checkIfEquipmentExists(id)) {
                map.put(id, true);
            }
            else {
                map.put(id, false);
            }
        }
        return ResponseEntity.ok(map);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить оборудование")
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
        equipmentService.softDeleteEquipment(id);
        return ResponseEntity.noContent().build();
    }
}
