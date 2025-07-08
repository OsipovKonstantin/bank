package ru.neoflex.calculator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.calculator.configuration.RateConfig;
import ru.neoflex.calculator.model.dto.PaymentScheduleElementDto;
import ru.neoflex.calculator.util.MoneyUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnnuityCalculatorServiceTest {

    @Mock
    private RateConfig rateConfig;

    @InjectMocks
    private AnnuityCalculatorService annuityCalculatorService;

    @Test
    void givenInsuranceEnabled_whenCalculateRequestedAmount_thenIncludeInsuranceAmount() {
        when(rateConfig.getInsuranceRate()).thenReturn(new BigDecimal("5.00"));

        BigDecimal requestedAmount = new BigDecimal("100000");
        BigDecimal result = annuityCalculatorService.calculateRequestedAmount(requestedAmount, true);

        BigDecimal expectedInsurance = requestedAmount.multiply(new BigDecimal("0.05"));
        assertEquals(requestedAmount.add(expectedInsurance), result);
    }

    @Test
    void givenInsuranceDisabled_whenCalculateRequestedAmount_thenReturnOriginalAmount() {
        when(rateConfig.getInsuranceRate()).thenReturn(new BigDecimal("5.00"));

        BigDecimal requestedAmount = new BigDecimal("100000");
        BigDecimal result = annuityCalculatorService.calculateRequestedAmount(requestedAmount, false);

        assertEquals(requestedAmount, result);
    }

    @Test
    void givenValidInputs_whenCalculateMonthlyPayment_thenReturnCorrectMonthlyPayment() {
        BigDecimal amount = new BigDecimal("100000");
        int term = 12;
        BigDecimal annualRate = new BigDecimal("12");

        BigDecimal monthlyRate = new BigDecimal("0.01");
        BigDecimal k = monthlyRate.add(BigDecimal.ONE).pow(term, MoneyUtils.SIXTEEN_DIGITS);
        BigDecimal expected = amount.multiply(monthlyRate).multiply(k)
                .divide(k.subtract(BigDecimal.ONE), MoneyUtils.SIXTEEN_DIGITS);

        BigDecimal result = annuityCalculatorService.calculateMonthlyPayment(amount, term, annualRate);
        assertEquals(expected, result);
    }

    @Test
    void givenMonthlyPaymentAndTerm_whenCalculateTotalAmount_thenReturnSumOfAllPayments() {
        BigDecimal monthly = new BigDecimal("10000");
        int term = 12;
        BigDecimal result = annuityCalculatorService.calculateTotalAmount(monthly, term);

        assertEquals(monthly.multiply(BigDecimal.valueOf(term)), result);
    }

    @Test
    void givenValidParameters_whenGeneratePaymentSchedule_thenReturnCorrectSchedule() {
        BigDecimal monthlyPayment = new BigDecimal("10000");
        int term = 3;
        BigDecimal rate = new BigDecimal("12");

        List<PaymentScheduleElementDto> schedule = annuityCalculatorService.generatePaymentsSchedule(
                term, monthlyPayment, new BigDecimal("30000"), rate);

        assertEquals(term, schedule.size());

        for (int i = 0; i < term; i++) {
            assertEquals(i + 1, schedule.get(i).number());
            assertEquals(LocalDate.now().plusMonths(i + 1), schedule.get(i).date());
        }
    }
}
