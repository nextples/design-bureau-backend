package ru.nsu.nextples.ms_projects.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.nextples.ms_projects.dto.subcontractor.SubcontractorCreateDTO;
import ru.nsu.nextples.ms_projects.dto.subcontractor.SubcontractorDTO;
import ru.nsu.nextples.ms_projects.dto.subcontractor.SubcontractorUpdateDTO;
import ru.nsu.nextples.ms_projects.service.SubcontractorService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/subcontractors")
@Tag(name = "Субподрядчики", description = "API для управления субподрядчиками")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubcontractorController {

    private final SubcontractorService subcontractorService;

    @PostMapping
    @Operation(summary = "Создать субподрядчика")
    public ResponseEntity<SubcontractorDTO> createSubcontractor(@Valid @RequestBody SubcontractorCreateDTO request) {
        SubcontractorDTO subcontractor = subcontractorService.createSubcontractor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(subcontractor);
    }

    @PutMapping("/{subcontractorId}")
    @Operation(summary = "Обновить субподрядчика")
    public ResponseEntity<SubcontractorDTO> updateSubcontractor(@Valid @PathVariable UUID subcontractorId,
                                                                @RequestBody SubcontractorUpdateDTO request) {
        SubcontractorDTO subcontractor = subcontractorService.updateSubcontractor(subcontractorId, request);
        return ResponseEntity.ok(subcontractor);
    }

    @GetMapping
    @Operation(summary = "Получить всех субподрядчиков")
    public ResponseEntity<List<SubcontractorDTO>> getAllSubcontractors(@RequestParam(required = false) String name) {
        List<SubcontractorDTO> subcontractors = subcontractorService.getAllSubcontractors(name);
        return ResponseEntity.ok(subcontractors);
    }

    @GetMapping("/{subcontractorID}")
    @Operation(summary = "Получить детали субподрядчика")
    public ResponseEntity<SubcontractorDTO> getSubcontractorById(@PathVariable UUID subcontractorID) {
        SubcontractorDTO subcontractor = subcontractorService.getSubcontractor(subcontractorID);
        return ResponseEntity.ok(subcontractor);
    }

    @DeleteMapping("/{subcontractorId}")
    @Operation(summary = "Удалить субподрядчика")
    public ResponseEntity<Void> deleteSubcontractorById(@PathVariable UUID subcontractorId) {
        subcontractorService.deleteSubcontractor(subcontractorId);
        return ResponseEntity.noContent().build();
    }
}
