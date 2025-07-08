package ru.neoflex.deal.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.With;
import ru.neoflex.deal.util.MoneyUtils;

import java.math.BigDecimal;
import java.util.UUID;

@With
@Builder
@Schema(description = "Кредитное предложение")
public record LoanOfferDto(
        @Schema(description = "Номер заявки", example = "09b84931-b217-426a-a618-4b51af2d3bb1")
        UUID statementId,
        @Schema(description = "Запрашиваемая сумма", example = "1000000.50")
        BigDecimal requestedAmount,
        @Schema(description = "Полная стоимость кредита (ПСК)", example = "331540.69")
        BigDecimal totalAmount,
        @Schema(description = "Срок кредита в месяцах", example = "40")
        Integer term,
        @Schema(description = "Ежемесячный платеж", example = "17560.31")
        BigDecimal monthlyPayment,
        @Schema(description = "Ставка по кредиту в %", example = "17.4")
        BigDecimal rate,
        @Schema(description = "Включена ли страховка", example = "true")
        Boolean isInsuranceEnabled,
        @Schema(description = "Являетесь ли зарплатным клиентом", example = "false")
        Boolean isSalaryClient
) {
    public LoanOfferDto {
        requestedAmount = MoneyUtils.moneyScale(requestedAmount);
        totalAmount = MoneyUtils.moneyScale(totalAmount);
        monthlyPayment = MoneyUtils.moneyScale(monthlyPayment);
        rate = MoneyUtils.rateScale(rate);
    }
}
