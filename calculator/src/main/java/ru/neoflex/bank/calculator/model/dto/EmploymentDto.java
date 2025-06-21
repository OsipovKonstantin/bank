package ru.neoflex.bank.calculator.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import ru.neoflex.bank.calculator.model.enums.EmploymentStatus;
import ru.neoflex.bank.calculator.model.enums.WorkPosition;

import java.math.BigDecimal;

import static ru.neoflex.bank.calculator.util.RegularExpressionConstants.TEN_DIGITS;

@Builder
@Schema(description = "Трудовые данные клиента")
public record EmploymentDto(
        @Schema(description = "Трудовой статус", example = "SELF_EMPLOYED")
        @NotNull(message = "Укажите трудовой статус")
        EmploymentStatus employmentStatus,
        @Schema(description = "ИНН работодателя", example = "7783572942")
        @NotBlank(message = "Введите ИНН работодателя")
        @Pattern(regexp = TEN_DIGITS, message = "ИНН работодателя должен состоять из 10 цифр")
        String EmployerINN,
        @Schema(description = "Заработная плата", example = "51400.5")
        @NotNull(message = "Заполните поле заработная плата")
        BigDecimal salary,
        @Schema(description = "Должность", example = "SPECIALIST")
        @NotNull(message = "Укажите должность")
        WorkPosition position,
        @Schema(description = "Общий стаж работы в месяцах", example = "20")
        @NotNull(message = "Укажите общий стаж работы в месяцах")
        Integer workExperienceTotal,
        @Schema(description = "Текущий стаж работы в месяцах", example = "8")
        @NotNull(message = "Укажите текущий стаж в месяцах")
        Integer workExperienceCurrent
) {
}