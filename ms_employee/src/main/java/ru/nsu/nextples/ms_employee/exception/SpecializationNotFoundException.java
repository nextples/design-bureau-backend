package ru.nsu.nextples.ms_employee.exception;

import java.util.UUID;

public class SpecializationNotFoundException extends ObjectNotFoundException {
    public SpecializationNotFoundException(UUID id) {
        super("Engineer specialization with id " + id + " not found");
    }
}
