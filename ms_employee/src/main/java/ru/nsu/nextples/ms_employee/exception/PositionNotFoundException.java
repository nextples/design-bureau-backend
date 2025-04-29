package ru.nsu.nextples.ms_employee.exception;

import java.util.UUID;

public class PositionNotFoundException extends ObjectNotFoundException {
    public PositionNotFoundException(UUID id) {
        super("Employee position with id " + id + " not found");
    }
}
