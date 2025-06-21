package ru.neoflex.bank.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsAdultValidator.class)
public @interface IsAdult {
    String message() default "Возраст должен быть от 18 лет";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
