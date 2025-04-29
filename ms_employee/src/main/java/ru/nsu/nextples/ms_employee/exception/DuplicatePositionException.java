package ru.nsu.nextples.ms_employee.exception;

import ru.nsu.nextples.ms_employee.model.EmployeeType;

public class DuplicatePositionException extends RuntimeException {
    public DuplicatePositionException(EmployeeType type, String name) {
        super("Duplicate employee position found for type " + type + " and name " + name);
    }
}
