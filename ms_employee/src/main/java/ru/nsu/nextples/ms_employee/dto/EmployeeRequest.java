package ru.nsu.nextples.ms_employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EmployeeRequest {
    @NotBlank(message = "Firstname can not be empty")
    @Size(min = 2, max = 50, message = "The firstname must contain from 2 to 50 characters")
    private String firstName;

    @NotBlank(message = "Lastname can not be empty")
    @Size(min = 2, max = 50, message = "The lastname must contain from 2 to 50 characters")
    private String lastName;

    @Size(min = 16, message = "The age must be at least 16")
    private int age;

    @NotBlank(message = "Category ID is required")
    private UUID categoryId;

    @NotBlank(message = "Department ID is required")
    private UUID departmentId;
}
