package ru.neoflex.bank.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.With;
import ru.neoflex.bank.util.MoneyUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Schema(description = "Элемент графика платежей")
public record PaymentScheduleElementDto(
        @Schema(description = "Номер платежа", example = "1")
        Integer number,
        @Schema(description = "Дата платежа", example = "2025-04-05")
        LocalDate date,
        @Schema(description = "Ежемесячный платеж", example = "17560.31")
        BigDecimal totalPayment,
        @Schema(description = "Платеж в счет уплаты процентов", example = "12430.10")
        BigDecimal interestPayment,
        @Schema(description = "Платеж в счет погашения тела кредита", example = "5130.21")
        BigDecimal debtPayment,
        @Schema(description = "Остаток тела кредита после платежа", example = "994869.79")
        BigDecimal remainingDebt
) {
    public PaymentScheduleElementDto {
        totalPayment = MoneyUtils.moneyScale(totalPayment);
        interestPayment = MoneyUtils.moneyScale(interestPayment);
        debtPayment = MoneyUtils.moneyScale(debtPayment);
        remainingDebt = MoneyUtils.moneyScale(remainingDebt);
    }
}