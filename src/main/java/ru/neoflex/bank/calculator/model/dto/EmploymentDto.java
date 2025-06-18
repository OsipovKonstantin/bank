package ru.neoflex.bank.calculator.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import ru.neoflex.bank.calculator.model.enums.EmploymentStatus;
import ru.neoflex.bank.calculator.model.enums.WorkPosition;

import java.math.BigDecimal;

import static ru.neoflex.bank.calculator.util.RegularExpressionConstants.TEN_DIGITS;

public record EmploymentDto(
        @NotNull(message = "Укажите трудовой статус")
        EmploymentStatus employmentStatus,
        @NotBlank(message = "Введите ИНН работодателя")
        @Pattern(regexp = TEN_DIGITS, message = "ИНН работодателя должен состоять из 10 цифр")
        String EmployerINN,
        @NotNull(message = "Заполните поле заработная плата")
        BigDecimal salary,
        @NotNull(message = "Укажите должность")
        WorkPosition position,
        @NotNull(message = "Укажите общий стаж работы в месяцах")
        Integer workExperienceTotal,
        @NotNull(message = "Укажите текущий стаж в месяцах")
        Integer workExperienceCurrent
) {}