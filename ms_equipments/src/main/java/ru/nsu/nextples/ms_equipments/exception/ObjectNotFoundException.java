package ru.nsu.nextples.ms_equipments.exception;

import java.util.UUID;

public class ObjectNotFoundException extends RuntimeException {
  public ObjectNotFoundException(String object, UUID id) {
    super("Could not find " + object + " with id " + id);
  }
}
