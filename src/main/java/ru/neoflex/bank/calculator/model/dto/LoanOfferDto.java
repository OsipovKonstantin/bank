package ru.neoflex.bank.calculator.model.dto;

import ru.neoflex.bank.calculator.util.MoneyUtils;

import java.math.BigDecimal;
import java.util.UUID;

public record LoanOfferDto(
        UUID statementId,
        BigDecimal requestedAmount,
        BigDecimal totalAmount,
        Integer term,
        BigDecimal monthlyPayment,
        BigDecimal rate,
        Boolean isInsuranceEnabled,
        Boolean isSalaryClient
) {
    public LoanOfferDto {
        requestedAmount = MoneyUtils.moneyScale(requestedAmount);
        totalAmount = MoneyUtils.moneyScale(totalAmount);
        monthlyPayment = MoneyUtils.moneyScale(monthlyPayment);
        rate = MoneyUtils.rateScale(rate);
    }
}
