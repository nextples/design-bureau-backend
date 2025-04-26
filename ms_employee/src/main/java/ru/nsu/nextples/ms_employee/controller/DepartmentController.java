package ru.nsu.nextples.ms_employee.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.nextples.ms_employee.dto.department.DepartmentCreateDTO;
import ru.nsu.nextples.ms_employee.dto_old.department.DepartmentResponseDTO;

@RestController
@RequestMapping("api/v1/departments")
@Tag(name = "Отделы", description = "API для управления отделами")
public class DepartmentController {


//    @PostMapping
//    public DepartmentResponseDTO createDepartment(@Valid @RequestBody DepartmentCreateDTO departmentCreateDTO) {
//
//    }
}
