package ru.nsu.nextples.ms_employee.exception;

import java.util.UUID;

public class EmployeePositionNotFoundException extends ObjectNotFoundException {
    public EmployeePositionNotFoundException(UUID id) {
        super("Employee position with id " + id + " not found");
    }
}
