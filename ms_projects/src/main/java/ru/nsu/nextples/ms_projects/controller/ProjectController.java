package ru.nsu.nextples.ms_projects.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.nextples.ms_projects.dto.equipment.AddEquipmentRequestDTO;
import ru.nsu.nextples.ms_projects.dto.project.ProjectCreateDTO;
import ru.nsu.nextples.ms_projects.dto.project.ProjectDTO;
import ru.nsu.nextples.ms_projects.dto.project.ProjectUpdateDTO;
import ru.nsu.nextples.ms_projects.model.ProjectStatus;
import ru.nsu.nextples.ms_projects.service.ProjectService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
@Tag(name = "Проекты", description = "API для управления проектами")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @Operation(summary = "Создать проект")
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectCreateDTO request) {
        ProjectDTO project = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @PutMapping("/{projectId}")
    @Operation(summary = "Обновить проект")
    public ResponseEntity<ProjectDTO> updateProject(@Valid @PathVariable UUID projectId,
                                                    @RequestBody ProjectUpdateDTO request) {
        ProjectDTO project = projectService.updateProject(projectId, request);
        return ResponseEntity.ok(project);
    }

    @GetMapping
    @Operation(summary = "Получить все проекты")
    public ResponseEntity<List<ProjectDTO>> getAllProjects(@RequestParam(required = false) ProjectStatus status,
                                                           @RequestParam(required = false) LocalDate startDate,
                                                           @RequestParam(required = false) LocalDate endDate,
                                                           @RequestParam(required = false) BigDecimal minCost,
                                                           @RequestParam(required = false) BigDecimal maxCost
    ) {
        List<ProjectDTO> projects = projectService.getAllProjects(
                status, startDate, endDate, minCost, maxCost
        );
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "Получить детальный проект")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable UUID projectId) {
        ProjectDTO project = projectService.getProject(projectId);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/{projectId}/employees")
    @Operation(summary = "Получить сотрудников, участвующих в проекте")
    public ResponseEntity<List<UUID>> getEmployees(@PathVariable UUID projectId) {
        List<UUID> employeeIds = projectService.getProjectEmployees(projectId);
        return ResponseEntity.ok(employeeIds);
    }

    @DeleteMapping("/{projectId}")
    @Operation(summary = "Удалить проект")
    public ResponseEntity<Void> deleteProjectById(@PathVariable UUID projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{projectId}/employees")
    @Operation(summary = "Добавить сотрудников в проект")
    public ResponseEntity<ProjectDTO> addEmployeesToProject(@PathVariable UUID projectId, @RequestBody List<UUID> employeeIds) {
        ProjectDTO project = projectService.addEmployeesToProject(projectId, employeeIds);
        return ResponseEntity.ok(project);
    }

    @PatchMapping("/{projectId}/employees/remove")
    @Operation(summary = "Удалить сотрудника из проекта")
    public ResponseEntity<ProjectDTO> removeEmployeeFromProject(@PathVariable UUID projectId, @RequestBody UUID employeeId) {
        ProjectDTO project = projectService.removeEmployeeFromProject(projectId, employeeId);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/{projectId}/equipments")
    @Operation(summary = "Добавить оборудование в проект")
    public ResponseEntity<ProjectDTO> addEquipmentsToProject(@PathVariable UUID projectId,
                                                             @Valid @RequestBody AddEquipmentRequestDTO request) {
        ProjectDTO project = projectService.addEquipmentToProject(projectId, request);
        return ResponseEntity.ok(project);
    }

    @PatchMapping("/{projectId}/equipments/remove")
    @Operation(summary = "Удалить оборудование из проекта")
    public ResponseEntity<ProjectDTO> removeEquipmentFromProject(@PathVariable UUID projectId, @RequestBody UUID equipmentId) {
        ProjectDTO project = projectService.removeEquipmentFromProject(projectId, equipmentId);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/{projectId}/progress")
    @Operation(summary = "Обновить прогресс по проекту")
    public ResponseEntity<ProjectDTO> updateProjectProgress(@PathVariable UUID projectId,
                                                            @Min(value = 0, message = "Минимальное значение прогресса 0%")
                                                            @Max(value = 100, message = "Максимальное значение прогресса 100%")
                                                            @RequestParam(name = "val") Integer newValue
    ) {
        ProjectDTO project = projectService.updateInternalProgress(projectId, newValue);
        return ResponseEntity.ok(project);
    }

    @PostMapping("/costs")
    @Operation(summary = "Получить стоимость указанных проектов")
    public ResponseEntity<Map<UUID, BigDecimal>> getProjectCosts(@RequestBody List<UUID> projectIds) {
        Map<UUID, BigDecimal> costs = projectService.getProjectCosts(projectIds);
        return ResponseEntity.ok(costs);
    }

    @GetMapping("/{projectId}/exists")
    @Operation(summary = "Проверить существование проекта")
    public ResponseEntity<Boolean> projectExists(@PathVariable UUID projectId) {
        return ResponseEntity.ok(projectService.projectExists(projectId));
    }

    @PostMapping("/exists")
    @Operation(summary = "Проверить существование проектов")
    public ResponseEntity<Boolean> projectExists(@RequestParam List<UUID> projectIds) {
        return ResponseEntity.ok(projectService.projectExists(projectIds));
    }
}
