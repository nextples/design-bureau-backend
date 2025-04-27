package ru.nsu.nextples.ms_employee.exception;

import java.util.UUID;

public class LaboratoryNotFoundException extends ObjectNotFoundException {
    public LaboratoryNotFoundException(UUID id) {
        super("Laboratory with id " + id + " not found");
    }
}
