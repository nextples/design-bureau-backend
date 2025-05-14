package ru.nsu.nextples.ms_contracts.exception;

import java.util.UUID;

public class DeleteConflictException extends RuntimeException {
    public DeleteConflictException(String object, UUID id) {
        super("Object " + object + " with ID " + id + " cannot be deleted because it has active dependencies");
    }
}
