package ru.nsu.nextples.ms_employee.exception;

import ru.nsu.nextples.ms_employee.model.EmployeeType;

import java.util.UUID;

public class InvalidPositionException extends RuntimeException {
    public InvalidPositionException(UUID positionId, EmployeeType type) {
        super("Position " + positionId + " is invalid for employee type " + type);
    }
}
