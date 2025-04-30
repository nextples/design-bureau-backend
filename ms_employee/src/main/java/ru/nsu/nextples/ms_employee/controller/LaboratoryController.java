package ru.nsu.nextples.ms_employee.controller;

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
import ru.nsu.nextples.ms_employee.dto.error.ErrorDTO;
import ru.nsu.nextples.ms_employee.dto.laboratory.LaboratoryCreateUpdateDTO;
import ru.nsu.nextples.ms_employee.dto.laboratory.LaboratoryDTO;
import ru.nsu.nextples.ms_employee.service.LaboratoryService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/labs")
@Tag(name = "Лаборатории", description = "API для управления лабораториями")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LaboratoryController {

    private final LaboratoryService laboratoryService;

    @PostMapping
    @Operation(summary = "Создать лабораторию")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LaboratoryDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<LaboratoryDTO> createLaboratory(
            @Valid @RequestBody LaboratoryCreateUpdateDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(laboratoryService.creteLaboratory(request));
    }


    @Operation(summary = "Обновить лабораторию")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LaboratoryDTO.class)
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
    public ResponseEntity<LaboratoryDTO> updateLaboratory(
            @PathVariable UUID id,
            @Valid @RequestBody LaboratoryCreateUpdateDTO request
    ) {
        return ResponseEntity.ok(laboratoryService.updateLaboratory(id, request));
    }


    @GetMapping
    @Operation(
            summary = "Получить список лабораторий",
            parameters = {
                    @Parameter(name = "id", description = "UUID лаборатории"),
                    @Parameter(name = "name", description = "Часть названия лаборатории"),
                    @Parameter(name = "page", description = "Номер страницы", example = "0"),
                    @Parameter(name = "size", description = "Размер страницы", example = "20")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = LaboratoryDTO.class))
                    )
            )
    })
    public ResponseEntity<Page<LaboratoryDTO>> getLaboratories(
            @RequestParam(required = false) UUID id,
            @RequestParam(required = false) String name,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(laboratoryService.getLaboratories(id, name, pageable));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить лабораторию")
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
            )
    })
    public ResponseEntity<Void> deleteLaboratory(@PathVariable UUID id) {
        laboratoryService.deleteLaboratory(id);
        return ResponseEntity.noContent().build();
    }
}
