package ru.neoflex.bank.calculator.service;

import ru.neoflex.bank.calculator.model.dto.PaymentScheduleElementDto;

import java.math.BigDecimal;
import java.util.List;

public interface AnnuityCalculatorService {
    BigDecimal calculateRequestedAmount(BigDecimal requestedAmount, boolean isInsuranceEnabled);

    BigDecimal calculateMonthlyPayment(BigDecimal amount, Integer term, BigDecimal rate);

    BigDecimal calculateTotalAmount(BigDecimal monthlyPayment, Integer term);

    List<PaymentScheduleElementDto> generatePaymentsSchedule(Integer term, BigDecimal monthlyPayment,
                                                             BigDecimal requestedAmount, BigDecimal rate);
}
