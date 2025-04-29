package ru.nsu.nextples.ms_employee.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.nsu.nextples.ms_employee.model.Employee;

public class ValidPositionValidator implements ConstraintValidator<ValidPosition, Employee> {

    @Override
    public boolean isValid(Employee employee, ConstraintValidatorContext context) {
        return employee.getPosition().getEmployeeType()
                == employee.getEmployeeType();
    }
}
