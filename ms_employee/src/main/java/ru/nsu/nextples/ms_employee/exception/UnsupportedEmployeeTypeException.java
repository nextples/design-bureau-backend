package ru.nsu.nextples.ms_employee.exception;

import ru.nsu.nextples.ms_employee.model.EmployeeType;

public class UnsupportedEmployeeTypeException extends RuntimeException {
    public UnsupportedEmployeeTypeException(EmployeeType employeeType) {
        super("Unsupported employee type: " + employeeType);
    }
}
