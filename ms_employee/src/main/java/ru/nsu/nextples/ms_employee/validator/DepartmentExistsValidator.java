package ru.nsu.nextples.ms_employee.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import ru.nsu.nextples.ms_employee.annotation.DepartmentExists;

@Component
public class DepartmentExistsValidator implements ConstraintValidator<DepartmentExists, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
