package ru.nsu.nextples.ms_employee.exception;

public class InvalidNameException extends RuntimeException {
  public InvalidNameException(String message) {
    super(message);
  }
}
