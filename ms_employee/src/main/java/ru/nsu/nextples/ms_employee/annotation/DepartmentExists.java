package ru.nsu.nextples.ms_employee.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.nsu.nextples.ms_employee.validator.DepartmentExistsValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DepartmentExistsValidator.class)
public @interface DepartmentExists {
    String message() default "Department does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
