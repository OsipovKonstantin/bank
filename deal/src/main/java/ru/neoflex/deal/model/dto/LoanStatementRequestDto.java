package ru.neoflex.deal.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.With;
import ru.neoflex.deal.annotation.IsAdult;
import ru.neoflex.deal.util.DateTimeUtils;
import ru.neoflex.deal.util.RegularExpressionConstants;

import java.math.BigDecimal;
import java.time.LocalDate;

@With
@Builder
@Schema(description = "Заявка на кредит")
public record LoanStatementRequestDto(
        @Schema(description = "Запрашиваемая сумма", example = "1000000.50")
        @NotNull(message = "Введите запрашиваемую сумму")
        @DecimalMin(value = "20000", message = "Минимальный размер кредита 20000 рублей")
        BigDecimal amount,
        @Schema(description = "Срок кредита в месяцах", example = "40")
        @NotNull(message = "Укажите срок кредита")
        @Min(value = 6, message = "Минимальный срок кредита 6 месяцев")
        Integer term,
        @Schema(description = "Имя", example = "Иван")
        @NotBlank(message = "Заполните имя")
        @Pattern(regexp = RegularExpressionConstants.LATIN_TWO_TO_THIRTY_SYMBOLS, message = "Имя должно быть от 2 до 30 латинских символов")
        String firstName,
        @Schema(description = "Фамилия", example = "Петров")
        @NotBlank(message = "Введите фамилию")
        @Pattern(regexp = RegularExpressionConstants.LATIN_TWO_TO_THIRTY_SYMBOLS, message = "Фамилия должна быть от 2 до 30 латинских символов")
        String lastName,
        @Schema(description = "Отчество", example = "Сидорович")
        @Pattern(regexp = RegularExpressionConstants.LATIN_TWO_TO_THIRTY_SYMBOLS, message = "Отчество должно быть от 2 до 30 латинских символов")
        String middleName,
        @Schema(description = "Электронная почта", example = "johndoe@gmail.com")
        @NotBlank(message = "Укажите email")
        @Pattern(regexp = RegularExpressionConstants.EMAIL_PATTERN, message = "Некорректный email")
        String email,
        @Schema(description = "Дата рождения", example = "2000-06-30")
        @NotNull(message = "Введите дату рождения")
        @IsAdult(message = "Минимальный возраст 18 лет")
        @JsonFormat(pattern = DateTimeUtils.DATE_PATTERN)
        LocalDate birthdate,
        @Schema(description = "Серия паспорта", example = "4013")
        @NotBlank(message = "Заполните серию паспорта")
        @Pattern(regexp = RegularExpressionConstants.FOUR_DIGITS, message = "Серия паспорта должна состоять из 4 цифр")
        String passportSeries,
        @Schema(description = "Номер паспорта", example = "049647")
        @NotBlank(message = "Укажите номер паспорта")
        @Pattern(regexp = RegularExpressionConstants.SIX_DIGITS, message = "Номер паспорта должен состоять из 6 цифр")
        String passportNumber
) {
}