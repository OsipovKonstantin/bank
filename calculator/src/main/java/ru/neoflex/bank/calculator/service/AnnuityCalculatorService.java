package ru.neoflex.bank.calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.bank.calculator.configuration.RateConfig;
import ru.neoflex.bank.model.dto.PaymentScheduleElementDto;
import ru.neoflex.bank.util.MoneyUtils;
import ru.neoflex.bank.logging.Logging;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Logging
@Service
@RequiredArgsConstructor
public class AnnuityCalculatorService {
    private final RateConfig rateConfig;

    public BigDecimal calculateRequestedAmount(BigDecimal requestedAmount, boolean isInsuranceEnabled) {
        BigDecimal insuranceRate = rateConfig.getInsuranceRate()
                .divide(MoneyUtils.HUNDRED, MoneyUtils.SIXTEEN_DIGITS);
        BigDecimal insuranceAmount = requestedAmount.multiply(insuranceRate);
        return isInsuranceEnabled ? requestedAmount.add(insuranceAmount) : requestedAmount;
    }

    public BigDecimal calculateMonthlyPayment(BigDecimal amount, Integer term, BigDecimal rate) {
        BigDecimal monthlyRate = MoneyUtils.calculateMonthlyRate(rate);
        BigDecimal k = monthlyRate.add(BigDecimal.ONE).pow(term, MoneyUtils.SIXTEEN_DIGITS);
        return amount.multiply(monthlyRate).multiply(k).divide(k.subtract(BigDecimal.ONE), MoneyUtils.SIXTEEN_DIGITS);
    }

    public BigDecimal calculateTotalAmount(BigDecimal monthlyPayment, Integer term) {
        return monthlyPayment.multiply(BigDecimal.valueOf(term));
    }

    public List<PaymentScheduleElementDto> generatePaymentsSchedule(Integer term, BigDecimal monthlyPayment,
                                                                    BigDecimal requestedAmount, BigDecimal rate) {
        List<PaymentScheduleElementDto> schedule = new ArrayList<>();
        BigDecimal monthlyRate = MoneyUtils.calculateMonthlyRate(rate);
        BigDecimal remainingDebt = requestedAmount;

        LocalDate currentDate = LocalDate.now();

        for (int month = 1; month <= term; month++) {
            BigDecimal interestPayment = remainingDebt.multiply(monthlyRate);
            BigDecimal debtPayment = monthlyPayment.subtract(interestPayment);
            remainingDebt = remainingDebt.subtract(debtPayment);

            schedule.add(PaymentScheduleElementDto.builder()
                    .number(month)
                    .date(currentDate.plusMonths(month))
                    .totalPayment(monthlyPayment)
                    .interestPayment(interestPayment)
                    .debtPayment(debtPayment)
                    .remainingDebt(remainingDebt).build());
        }
        return schedule;
    }
}
