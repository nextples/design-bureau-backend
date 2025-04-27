package ru.nsu.nextples.ms_employee.exception;

import java.util.UUID;

public class EmployeeObjectNotFoundException extends ObjectNotFoundException {
    public EmployeeObjectNotFoundException(UUID id) {
        super("Employee with id " + id + " not found");
    }
}
