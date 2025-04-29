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
import ru.nsu.nextples.ms_employee.dto.PositionCreateDTO;
import ru.nsu.nextples.ms_employee.dto.PositionDTO;
import ru.nsu.nextples.ms_employee.dto.PositionUpdateDTO;
import ru.nsu.nextples.ms_employee.dto.employee.EmployeeCreateDTO;
import ru.nsu.nextples.ms_employee.dto.employee.EmployeeDTO;
import ru.nsu.nextples.ms_employee.dto.error.ErrorDTO;
import ru.nsu.nextples.ms_employee.model.EmployeeType;
import ru.nsu.nextples.ms_employee.service.PositionService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/positions")
@Tag(name = "Должности", description = "Управление должностями сотрудников")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PositionController {

    private final PositionService positionService;

    @PostMapping
    @Operation(summary = "Создать должность")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class)
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
    public ResponseEntity<PositionDTO> createEmployee(@Valid @RequestBody PositionCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(positionService.createPosition(request));
    }


    @Operation(summary = "Обновить должность")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeCreateDTO.class)
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
    public ResponseEntity<PositionDTO> updateEmployee(
            @PathVariable UUID id,
            @Valid @RequestBody PositionUpdateDTO request
    ) {
        return ResponseEntity.ok(positionService.updatePosition(id, request));
    }


    @GetMapping
    @Operation(
            summary = "Получить список должностей",
            parameters = {
                    @Parameter(name = "type", description = "Тип сотрудника"),
                    @Parameter(name = "name", description = "Часть названия должности"),
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
                            array = @ArraySchema(schema = @Schema(implementation = EmployeeDTO.class))
                    )
            )
    })
    public ResponseEntity<Page<PositionDTO>> getAllPositions(
            @RequestParam(required = false) EmployeeType type,
            @RequestParam(required = false) String name,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(positionService.getAllPositions(type, name, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию о должности")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class)
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
    public ResponseEntity<PositionDTO> getEmployeeDetails(@PathVariable UUID id) {
        return ResponseEntity.ok(positionService.getPosition(id));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить должность")
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
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        positionService.deletePosition(id);
        return ResponseEntity.noContent().build();
    }
}
