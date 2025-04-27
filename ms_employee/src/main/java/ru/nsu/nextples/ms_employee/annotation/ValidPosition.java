package ru.nsu.nextples.ms_employee.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidPositionValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
public @interface ValidPosition {
    String message() default "Position does not match employee type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
