package ru.neoflex.deal.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.With;
import ru.neoflex.deal.annotation.IsAdult;
import ru.neoflex.deal.model.enums.Gender;
import ru.neoflex.deal.model.enums.MaritalStatus;
import ru.neoflex.deal.util.DateTimeUtils;
import ru.neoflex.deal.util.RegularExpressionConstants;

import java.math.BigDecimal;
import java.time.LocalDate;

@With
@Builder
@Schema(description = "Данные для скоринга кредита")
public record ScoringDataDto(
        @Schema(description = "Сумма кредита", example = "1000000.50")
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
        @NotBlank(message = "Укажите фамилию")
        @Pattern(regexp = RegularExpressionConstants.LATIN_TWO_TO_THIRTY_SYMBOLS, message = "Имя должно быть от 2 до 30 латинских символов")
        String lastName,
        @Schema(description = "Отчество", example = "Сидорович")
        @Pattern(regexp = RegularExpressionConstants.LATIN_TWO_TO_THIRTY_SYMBOLS, message = "Имя должно быть от 2 до 30 латинских символов")
        String middleName,
        @Schema(description = "Пол", example = "MALE")
        @NotNull(message = "Укажите пол")
        Gender gender,
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
        String passportNumber,
        @Schema(description = "Дата выдачи паспорта", example = "2014-07-02")
        @NotNull(message = "Заполните дату выдачи паспорта")
        @JsonFormat(pattern = DateTimeUtils.DATE_PATTERN)
        LocalDate passportIssueDate,
        @Schema(description = "Кем выдан паспорт", example = "УФМС России по г. Москве")
        @NotBlank(message = "Введите кем выдан паспорт")
        String passportIssueBranch,
        @Schema(description = "Семейное положение", example = "MARRIED")
        @NotNull(message = "Укажите семейное положение")
        MaritalStatus maritalStatus,
        @Schema(description = "Количество людей, которые зависят от вас финансово", example = "2")
        @NotNull(message = "Заполните количество людей, которые зависят от вас финансово")
        Integer dependentAmount,
        @Schema(description = "Трудовые данные")
        @Valid
        @NotNull(message = "Укажите трудовые данные")
        EmploymentDto employment,
        @Schema(description = "Номер банковского счета вашей карты", example = "40817810700030001234")
        @NotBlank(message = "Введите номер банковского счета вашей карты")
        @Pattern(regexp = RegularExpressionConstants.TWENTY_DIGITS, message = "Номер банковского счета должен состоять из 20 цифр")
        String accountNumber,
        @Schema(description = "Включена ли страховка", example = "true")
        @NotNull(message = "Заполните включается ли страховка на недвижимость")
        Boolean isInsuranceEnabled,
        @Schema(description = "Являетесь ли зарплатным клиентом", example = "false")
        @NotNull(message = "Укажите являетесь ли зарплатным клиентом банка")
        Boolean isSalaryClient
) {
}