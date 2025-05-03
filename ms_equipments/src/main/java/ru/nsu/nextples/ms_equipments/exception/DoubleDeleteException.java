package ru.nsu.nextples.ms_equipments.exception;

import java.util.UUID;

public class DoubleDeleteException extends RuntimeException {
    public DoubleDeleteException(String object, UUID id) {
        super("Object " + object + " with id " + id + " already was marked as deleted");
    }
}
