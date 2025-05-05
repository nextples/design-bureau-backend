package ru.nsu.nextples.ms_equipments.exception;

import java.util.UUID;

public class ObjectNotAvailableException extends RuntimeException {
    public ObjectNotAvailableException(String object, UUID id) {
        super("Object " + object + " with ID " + id + " has some dependencies or is not available");
    }
}
