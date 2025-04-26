package ru.nsu.nextples.ms_employee.exception;

import java.util.UUID;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(UUID id) {
        super("Department with id " + id + " not found");
    }
}
