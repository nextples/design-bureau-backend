package ru.nsu.nextples.ms_contracts.exception;

import java.util.Collection;
import java.util.UUID;

public class ObjectNotFoundException extends RuntimeException {
  public ObjectNotFoundException(String object, UUID id) {
    super("Could not find " + object + " with id " + id);
  }

  public ObjectNotFoundException(String object, Collection<UUID> ids) {
    super("Could not find one or more " + object + " from " + ids.toString());
  }
}
