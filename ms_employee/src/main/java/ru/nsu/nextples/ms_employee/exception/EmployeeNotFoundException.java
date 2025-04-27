package ru.nsu.nextples.ms_employee.exception;

import java.util.UUID;

public class EmployeeNotFoundException extends ObjectNotFoundException {
    public EmployeeNotFoundException(UUID id) {
        super("Employee with id " + id + " not found");
    }
}
