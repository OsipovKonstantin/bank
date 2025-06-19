package ru.neoflex.bank.calculator.model.dto;

import ru.neoflex.bank.calculator.util.MoneyUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentScheduleElementDto(
        Integer number,
        LocalDate date,
        BigDecimal totalPayment,
        BigDecimal interestPayment,
        BigDecimal debtPayment,
        BigDecimal remainingDebt
) {
    public PaymentScheduleElementDto {
        totalPayment = MoneyUtils.moneyScale(totalPayment);
        interestPayment = MoneyUtils.moneyScale(interestPayment);
        debtPayment = MoneyUtils.moneyScale(debtPayment);
        remainingDebt = MoneyUtils.moneyScale(remainingDebt);
    }
}