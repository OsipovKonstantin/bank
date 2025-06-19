package ru.neoflex.bank.calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.bank.calculator.configuration.RateConfig;
import ru.neoflex.bank.calculator.model.dto.PaymentScheduleElementDto;
import ru.neoflex.bank.calculator.util.MoneyUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnuityCalculatorServiceImpl implements AnnuityCalculatorService {
    private final RateConfig rateConfig;

    @Override
    public BigDecimal calculateRequestedAmount(BigDecimal requestedAmount, boolean isInsuranceEnabled) {
        BigDecimal insuranceRate = rateConfig.getInsuranceRate()
                .divide(MoneyUtils.HUNDRED, MoneyUtils.SIXTEEN_DIGITS);
        BigDecimal insuranceAmount = requestedAmount.multiply(insuranceRate);
        return isInsuranceEnabled ? requestedAmount.add(insuranceAmount) : requestedAmount;
    }

    @Override
    public BigDecimal calculateMonthlyPayment(BigDecimal amount, Integer term, BigDecimal rate) {
        BigDecimal monthlyRate = MoneyUtils.calculateMonthlyRate(rate);
        BigDecimal k = monthlyRate.add(BigDecimal.ONE).pow(term, MoneyUtils.SIXTEEN_DIGITS);
        return amount.multiply(monthlyRate).multiply(k).divide(k.subtract(BigDecimal.ONE), MoneyUtils.SIXTEEN_DIGITS);
    }

    @Override
    public BigDecimal calculateTotalAmount(BigDecimal monthlyPayment, Integer term) {
        return monthlyPayment.multiply(BigDecimal.valueOf(term));
    }

    @Override
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

            schedule.add(new PaymentScheduleElementDto(
                    month,
                    currentDate.plusMonths(month),
                    monthlyPayment,
                    interestPayment,
                    debtPayment,
                    remainingDebt
            ));
        }
        return schedule;
    }
}
