package ru.nsu.nextples.ms_employee.exception;

import java.util.UUID;

public class DepartmentHasEmployeesDeleteException extends ObjectDeleteException {
    public DepartmentHasEmployeesDeleteException(UUID id) {
        super("Department " + id + " can not be deleted because it has employees");
    }
}
