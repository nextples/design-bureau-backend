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
import ru.nsu.nextples.ms_employee.dto.employee.engineer.EngineerSpecializationCreateUpdateDTO;
import ru.nsu.nextples.ms_employee.dto.employee.engineer.EngineerSpecializationDTO;
import ru.nsu.nextples.ms_employee.dto.error.ErrorDTO;
import ru.nsu.nextples.ms_employee.service.EngineerSpecializationService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/specializations")
@Tag(name = "Специализации инженера", description = "Управление специализациями инженеров")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EngineerSpecializationController {

    private final EngineerSpecializationService specializationService;

    @PostMapping
    @Operation(summary = "Создать специализацию")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EngineerSpecializationDTO.class)
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
    public ResponseEntity<EngineerSpecializationDTO> createSpecialization(
            @Valid @RequestBody EngineerSpecializationCreateUpdateDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(specializationService.createSpecialization(request));
    }


    @Operation(summary = "Обновить специализацию")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EngineerSpecializationDTO.class)
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
    public ResponseEntity<EngineerSpecializationDTO> updateSpecialization(
            @PathVariable UUID id,
            @Valid @RequestBody EngineerSpecializationCreateUpdateDTO request
    ) {
        return ResponseEntity.ok(specializationService.updateSpecialization(id, request));
    }


    @GetMapping
    @Operation(
            summary = "Получить список специализаций",
            parameters = {
                    @Parameter(name = "id", description = "UUID специализации"),
                    @Parameter(name = "name", description = "Часть названия специализации"),
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
                            array = @ArraySchema(schema = @Schema(implementation = EngineerSpecializationDTO.class))
                    )
            )
    })
    public ResponseEntity<Page<EngineerSpecializationDTO>> getSpecializations(
            @RequestParam(required = false) UUID id,
            @RequestParam(required = false) String name,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(specializationService.getSpecializations(id, name, pageable));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить специализацию")
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
    public ResponseEntity<Void> deleteSpecialization(@PathVariable UUID id) {
        specializationService.deleteSpecialization(id);
        return ResponseEntity.noContent().build();
    }
}
