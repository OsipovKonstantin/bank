package ru.neoflex.deal.model.jsonb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.With;
import ru.neoflex.deal.util.MoneyUtils;

import java.math.BigDecimal;
import java.util.UUID;

@With
@Builder
public record AppliedOffer(
        @JsonProperty("statement_id")
        UUID statementId,
        @JsonProperty("requested_amount")
        BigDecimal requestedAmount,
        @JsonProperty("total_amount")
        BigDecimal totalAmount,
        Integer term,
        @JsonProperty("monthly_payment")
        BigDecimal monthlyPayment,
        BigDecimal rate,
        @JsonProperty("insurance_enabled")
        Boolean isInsuranceEnabled,
        @JsonProperty("salary_client")
        Boolean isSalaryClient
) {
    public AppliedOffer {
        requestedAmount = MoneyUtils.moneyScale(requestedAmount);
        totalAmount = MoneyUtils.moneyScale(totalAmount);
        monthlyPayment = MoneyUtils.moneyScale(monthlyPayment);
        rate = MoneyUtils.rateScale(rate);
    }
}
