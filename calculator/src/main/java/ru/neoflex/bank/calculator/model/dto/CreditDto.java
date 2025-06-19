package ru.neoflex.bank.calculator.model.dto;

import ru.neoflex.bank.calculator.util.MoneyUtils;

import java.math.BigDecimal;
import java.util.List;

public record CreditDto(
        BigDecimal amount,
        Integer term,
        BigDecimal monthlyPayment,
        BigDecimal rate,
        BigDecimal psk,
        Boolean isInsuranceEnabled,
        Boolean isSalaryClient,
        List<PaymentScheduleElementDto> paymentSchedule
) {
    public CreditDto {
        amount = MoneyUtils.moneyScale(amount);
        monthlyPayment = MoneyUtils.moneyScale(monthlyPayment);
        rate = MoneyUtils.rateScale(rate);
        psk = MoneyUtils.moneyScale(psk);
    }
}
