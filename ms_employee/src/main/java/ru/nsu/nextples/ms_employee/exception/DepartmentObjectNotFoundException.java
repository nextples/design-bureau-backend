package ru.nsu.nextples.ms_employee.exception;

import java.util.UUID;

public class DepartmentObjectNotFoundException extends ObjectNotFoundException {
    public DepartmentObjectNotFoundException(UUID id) {
        super("Department with id " + id + " not found");
    }
}
