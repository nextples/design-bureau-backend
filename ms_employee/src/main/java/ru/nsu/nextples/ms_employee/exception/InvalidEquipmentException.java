package ru.nsu.nextples.ms_employee.exception;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class InvalidEquipmentException extends RuntimeException {
    private final List<UUID> invalidIds;

    public InvalidEquipmentException(List<UUID> invalidIds) {
        super("Invalid equipment IDs: " + invalidIds);
        this.invalidIds = invalidIds;
    }
}
