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
import ru.nsu.nextples.ms_employee.dto.employee.EmployeeCreateDTO;
import ru.nsu.nextples.ms_employee.dto.employee.EmployeeDTO;
import ru.nsu.nextples.ms_employee.dto.employee.EmployeeUpdateDTO;
import ru.nsu.nextples.ms_employee.dto.error.ErrorDTO;
import ru.nsu.nextples.ms_employee.model.EmployeeType;
import ru.nsu.nextples.ms_employee.service.EmployeeService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employee")
@Tag(name = "Сотрудники", description = "API для управления сотрудниками")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @Operation(summary = "Создать сотрудника")
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
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(request));
    }


    @Operation(summary = "Обновить сотрудника")
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
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeeUpdateDTO request
    ) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, request));
    }


    @GetMapping
    @Operation(
            summary = "Получить список сотрудников",
            description = "Фильтрация по различным параметрам с пагинацией",
            parameters = {
                    @Parameter(name = "firstName", description = "Часть имени (поиск по вхождению)"),
                    @Parameter(name = "lastName", description = "Часть фамилии (поиск по вхождению)"),
                    @Parameter(name = "ageFrom", description = "Возраст (нижняя граница)"),
                    @Parameter(name = "ageTo", description = "Возраст (верхняя граница)"),
                    @Parameter(name = "employeeType", description = "Тип сотрудника",
                            schema = @Schema(implementation = EmployeeType.class)),
                    @Parameter(name = "positionId", description = "ID должности из справочника"),
                    @Parameter(name = "page", description = "Номер страницы (начиная с 0)"),
                    @Parameter(name = "size", description = "Размер страницы (по умолчанию 20)"),
                    @Parameter(name = "sort", description = "Сортировка (формат: поле,asc|desc)")
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
    public ResponseEntity<Page<EmployeeDTO>> getAllEmployees(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Integer ageFrom,
            @RequestParam(required = false) Integer ageTo,
            @RequestParam(required = false) EmployeeType employeeType,
            @RequestParam(required = false) UUID positionId,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(
                employeeService.getAllEmployees(
                        firstName, lastName, ageFrom, ageTo, employeeType, positionId, pageable
                )
        );
    }


    @GetMapping("/{id}")
    @Operation(summary = "Получить детальную информацию о сотруднике")
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
    public ResponseEntity<EmployeeDTO> getEmployeeDetails(@PathVariable UUID id) {
        return ResponseEntity.ok(employeeService.getEmployeeDetails(id));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить сотрудника")
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
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
