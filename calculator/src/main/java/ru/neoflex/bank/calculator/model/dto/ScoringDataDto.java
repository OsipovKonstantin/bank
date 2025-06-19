package ru.neoflex.bank.calculator.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import ru.neoflex.bank.calculator.annotation.IsAdult;
import ru.neoflex.bank.calculator.model.enums.Gender;
import ru.neoflex.bank.calculator.model.enums.MaritalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

import static ru.neoflex.bank.calculator.util.DateTimeUtils.DATE_PATTERN;
import static ru.neoflex.bank.calculator.util.RegularExpressionConstants.*;

public record ScoringDataDto(
        @NotNull(message = "Введите запрашиваемую сумму")
        @DecimalMin(value = "20000", message = "Минимальный размер кредита 20000 рублей")
        BigDecimal amount,
        @NotNull(message = "Укажите срок кредита")
        @Min(value = 6, message = "Минимальный срок кредита 6 месяцев")
        Integer term,
        @NotBlank(message = "Заполните имя")
        @Pattern(regexp = LATIN_TWO_TO_THIRTY_SYMBOLS, message = "Имя должно быть от 2 до 30 латинских символов")
        String firstName,
        @NotBlank(message = "Укажите имя")
        @Pattern(regexp = LATIN_TWO_TO_THIRTY_SYMBOLS, message = "Имя должно быть от 2 до 30 латинских символов")
        String lastName,
        @NotBlank(message = "Введите имя")
        @Pattern(regexp = LATIN_TWO_TO_THIRTY_SYMBOLS, message = "Имя должно быть от 2 до 30 латинских символов")
        String middleName,
        @NotNull(message = "Укажите пол")
        Gender gender,
        @NotNull(message = "Введите дату рождения")
        @IsAdult(message = "Минимальный возраст 18 лет")
        @JsonFormat(pattern = DATE_PATTERN)
        LocalDate birthdate,
        @NotBlank(message = "Заполните серию паспорта")
        @Pattern(regexp = FOUR_DIGITS, message = "Серия паспорта должна состоять из 4 цифр")
        String passportSeries,
        @NotBlank(message = "Укажите номер паспорта")
        @Pattern(regexp = SIX_DIGITS, message = "Номер паспорта должен состоять из 6 цифр")
        String passportNumber,
        @NotNull(message = "Заполните дату выдачи паспорта")
        @JsonFormat(pattern = DATE_PATTERN)
        LocalDate passportIssueDate,
        @NotBlank(message = "Введите кем выдан паспорт")
        String passportIssueBranch,
        @NotNull(message = "Укажите семейное положение")
        MaritalStatus maritalStatus,
        @NotNull(message = "Заполните количество людей, которые от вас финансово зависят")
        Integer dependentAmount,
        @Valid
        @NotNull(message = "Укажите трудовые данные")
        EmploymentDto employment,
        @NotBlank(message = "Введите номер банковского счета вашей карты")
        @Pattern(regexp = TWENTY_DIGITS, message = "Номер банковского счета должен состоять из 20 цифр")
        String accountNumber,
        @NotNull(message = "Заполните включается ли страховка на недвижимость")
        Boolean isInsuranceEnabled,
        @NotNull(message = "Укажите являетесь ли зарплатным клиентом банка")
        Boolean isSalaryClient
) {}