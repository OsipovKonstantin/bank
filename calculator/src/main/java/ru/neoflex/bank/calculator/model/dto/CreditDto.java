package ru.neoflex.bank.calculator.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.neoflex.bank.calculator.util.MoneyUtils;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Параметры выдаваемого кредита")
public record CreditDto(
        @Schema(description = "Сумма кредита", example = "1000000.50")
        BigDecimal amount,
        @Schema(description = "Срок кредита в месяцах", example = "40")
        Integer term,
        @Schema(description = "Ежемесячный платеж", example = "17560.31")
        BigDecimal monthlyPayment,
        @Schema(description = "Ставка по кредиту в %", example = "17.4")
        BigDecimal rate,
        @Schema(description = "Полная стоимость кредита (ПСК)", example = "331540.69")
        BigDecimal psk,
        @Schema(description = "Включена ли страховка", example = "true")
        Boolean isInsuranceEnabled,
        @Schema(description = "Являетесь ли зарплатным клиентом", example = "false")
        Boolean isSalaryClient,
        @Schema(description = "График платежей")
        List<PaymentScheduleElementDto> paymentSchedule
) {
    public CreditDto {
        amount = MoneyUtils.moneyScale(amount);
        monthlyPayment = MoneyUtils.moneyScale(monthlyPayment);
        rate = MoneyUtils.rateScale(rate);
        psk = MoneyUtils.moneyScale(psk);
    }
}
