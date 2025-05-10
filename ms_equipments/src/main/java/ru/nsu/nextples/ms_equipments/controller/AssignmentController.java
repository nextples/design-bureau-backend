package ru.nsu.nextples.ms_equipments.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.nextples.ms_equipments.dto.assignment.AssignmentDTO;
import ru.nsu.nextples.ms_equipments.dto.assignment.AssignmentRequestDTO;
import ru.nsu.nextples.ms_equipments.dto.assignment.ReturnRequestDTO;
import ru.nsu.nextples.ms_equipments.dto.error.ErrorDTO;
import ru.nsu.nextples.ms_equipments.service.AssignmentService;

import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assignments")
@Tag(name = "Назначения", description = "API для назначения оборудования")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping("/project/{equipmentId}")
    @Operation(summary = "Назначить оборудование на проект")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AssignmentDTO.class)
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
    public ResponseEntity<AssignmentDTO> assignToProject(
            @PathVariable UUID equipmentId,
            @Valid @RequestBody AssignmentRequestDTO request
    ) {
        AssignmentDTO assignment = assignmentService.assignToProject(equipmentId, request);
        return ResponseEntity.created(URI.create("/assignments/" + assignment.getId()))
                .body(assignment);
    }


    @PostMapping("/project/{assignmentId}/return")
    @Operation(summary = "Вернуть оборудование из проекта")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AssignmentDTO.class)
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
    public ResponseEntity<AssignmentDTO> returnFromProject(
            @PathVariable UUID assignmentId,
            @Valid @RequestBody ReturnRequestDTO request
    ) {
        AssignmentDTO assignment = assignmentService.returnFromProject(assignmentId, request);
        return ResponseEntity.ok(assignment);
    }


    @GetMapping("/equipment/{equipmentId}/history")
    @Operation(summary = "Получить историю назначений оборудования")
    public ResponseEntity<Page<AssignmentDTO>> getAssignmentHistory(
            @PathVariable UUID equipmentId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @ParameterObject Pageable pageable
    ) {
        Page<AssignmentDTO> history = assignmentService.getAssignmentHistory(
                equipmentId,
                startDate,
                endDate,
                pageable
        );
        return ResponseEntity.ok(history);
    }
}
