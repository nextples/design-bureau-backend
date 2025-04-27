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
import ru.nsu.nextples.ms_employee.dto.department.DepartmentCreateDTO;
import ru.nsu.nextples.ms_employee.dto.department.DepartmentDTO;
import ru.nsu.nextples.ms_employee.dto.department.DepartmentUpdateDTO;
import ru.nsu.nextples.ms_employee.dto.error.ErrorDTO;
import ru.nsu.nextples.ms_employee.dto.error.ValidationErrorDTO;
import ru.nsu.nextples.ms_employee.service.DepartmentService;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/departments")
@Tag(name = "Отделы", description = "API для управления отделами")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    @Operation(summary = "Создать отдел")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DepartmentDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentCreateDTO departmentCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                departmentService.createDepartment(departmentCreateDTO)
        );
    }


    @Operation(summary = "Обновить отдел")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DepartmentDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorDTO.class)
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
    public ResponseEntity<DepartmentDTO> updateDepartment(
            @PathVariable UUID id,
            @Valid @RequestBody DepartmentUpdateDTO departmentUpdateDTO
    ) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentUpdateDTO));
    }


    @GetMapping
    @Operation(summary = "Получить список всех отделов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DepartmentDTO.class))
                    )
            )
    })
    public ResponseEntity<Page<DepartmentDTO>> getAllDepartments(
            @Parameter(description = "Фильтр по названию")
            @RequestParam(required = false) String name,
            @Parameter(description = "Фильтр по руководителю")
            @RequestParam(required = false) UUID headId,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(departmentService.getAllDepartments(name, headId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить детальную информацию об отделе")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DepartmentDTO.class)
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
    public ResponseEntity<DepartmentDTO> getDepartmentDetails(@PathVariable UUID id) {
        return ResponseEntity.ok(departmentService.getDepartmentDetails(id));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить отдел")
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
    public ResponseEntity<Void> deleteDepartment(@PathVariable UUID id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
