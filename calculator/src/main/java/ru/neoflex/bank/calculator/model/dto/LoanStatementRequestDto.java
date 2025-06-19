package ru.neoflex.bank.calculator.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import ru.neoflex.bank.calculator.annotation.IsAdult;

import java.math.BigDecimal;
import java.time.LocalDate;

import static ru.neoflex.bank.calculator.util.DateTimeUtils.DATE_PATTERN;
import static ru.neoflex.bank.calculator.util.RegularExpressionConstants.*;

public record LoanStatementRequestDto(
        @NotNull(message = "Введите запрашиваемую сумму")
        @DecimalMin(value = "20000", message = "Минимальный размер кредита 20000 рублей")
        BigDecimal amount,
        @NotNull(message = "Укажите срок кредита")
        @Min(value = 6, message = "Минимальный срок кредита 6 месяцев")
        Integer term,
        @NotBlank(message = "Заполните имя")
        @Pattern(regexp = LATIN_TWO_TO_THIRTY_SYMBOLS, message = "Имя должно быть от 2 до 30 латинских символов")
        String firstName,
        @NotBlank(message = "Введите фамилию")
        @Pattern(regexp = LATIN_TWO_TO_THIRTY_SYMBOLS, message = "Фамилия должна быть от 2 до 30 латинских символов")
        String lastName,
        @Pattern(regexp = LATIN_TWO_TO_THIRTY_SYMBOLS, message = "Отчество должно быть от 2 до 30 латинских символов")
        String middleName,
        @NotBlank(message = "Укажите email")
        @Pattern(regexp = EMAIL_PATTERN, message = "Некорректный email")
        String email,
        @NotNull(message = "Введите дату рождения")
        @IsAdult(message = "Минимальный возраст 18 лет")
        @JsonFormat(pattern = DATE_PATTERN)
        LocalDate birthdate,
        @NotBlank(message = "Заполните серию паспорта")
        @Pattern(regexp = FOUR_DIGITS, message = "Серия паспорта должна состоять из 4 цифр")
        String passportSeries,
        @NotBlank(message = "Укажите номер паспорта")
        @Pattern(regexp = SIX_DIGITS, message = "Номер паспорта должен состоять из 6 цифр")
        String passportNumber
) {
}