package ru.neoflex.bank.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class IsAdultValidator implements ConstraintValidator<IsAdult, LocalDate> {
    @Override
    public boolean isValid(LocalDate birthdate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate date = LocalDate.now().minusYears(18);
        return !birthdate.isAfter(date);
    }
}
