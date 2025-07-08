package ru.neoflex.deal.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.With;
import ru.neoflex.deal.model.enums.Gender;
import ru.neoflex.deal.model.enums.MaritalStatus;
import ru.neoflex.deal.util.DateTimeUtils;
import ru.neoflex.deal.util.RegularExpressionConstants;

import java.time.LocalDate;

@With
@Builder
@Schema(description = "Данные о клиенте при финальной регистрации заявки")
public record FinishRegistrationRequestDto(
        @Schema(description = "Пол", example = "MALE")
        @NotNull(message = "Укажите пол")
        Gender gender,
        @Schema(description = "Семейное положение", example = "MARRIED")
        @NotNull(message = "Укажите семейное положение")
        MaritalStatus maritalStatus,
        @Schema(description = "Количество людей, которые зависят от вас финансово", example = "2")
        @NotNull(message = "Заполните количество людей, которые зависят от вас финансово")
        Integer dependentAmount,
        @Schema(description = "Дата выдачи паспорта", example = "2014-07-02")
        @NotNull(message = "Заполните дату выдачи паспорта")
        @JsonFormat(pattern = DateTimeUtils.DATE_PATTERN)
        LocalDate passportIssueDate,
        @Schema(description = "Кем выдан паспорт", example = "УФМС России по г. Москве")
        @NotBlank(message = "Введите кем выдан паспорт")
        String passportIssueBranch,
        @Schema(description = "Трудовые данные")
        @Valid
        @NotNull(message = "Укажите трудовые данные")
        EmploymentDto employment,
        @Schema(description = "Номер банковского счета вашей карты", example = "40817810700030001234")
        @NotBlank(message = "Введите номер банковского счета вашей карты")
        @Pattern(regexp = RegularExpressionConstants.TWENTY_DIGITS,
                message = "Номер банковского счета должен состоять из 20 цифр")
        String accountNumber
) {}
