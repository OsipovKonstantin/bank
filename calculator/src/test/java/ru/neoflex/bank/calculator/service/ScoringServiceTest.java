package ru.neoflex.bank.calculator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.bank.calculator.configuration.RateConfig;
import ru.neoflex.bank.calculator.exception.ScoringRejectException;
import ru.neoflex.bank.common.model.dto.CreditDto;
import ru.neoflex.bank.common.model.dto.EmploymentDto;
import ru.neoflex.bank.common.model.dto.PaymentScheduleElementDto;
import ru.neoflex.bank.common.model.dto.ScoringDataDto;
import ru.neoflex.bank.common.model.enums.EmploymentStatus;
import ru.neoflex.bank.common.util.TestUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoringServiceTest {

    @Spy
    @InjectMocks
    private ScoringService scoringService;

    @Mock
    private AnnuityCalculatorService annuityCalculatorService;

    @Mock
    private RateAdjustmentService rateAdjustmentService;

    @Mock
    private RateConfig rateConfig;

    @ParameterizedTest
    @MethodSource("invalidParamsWithExceptionMessage")
    void givenInvalidScoringDataDto_whenValidateScoring_thenThrowException(ScoringDataDto dto) {
        assertThrows(ScoringRejectException.class, () -> scoringService.validateScoring(dto));
    }

    static List<ScoringDataDto> invalidParamsWithExceptionMessage() {
        ScoringDataDto validScoringDataDto = TestUtils.getValidScoringDataDto();
        EmploymentDto validEmploymentDto = TestUtils.getValidEmployment();
        return List.of(
                validScoringDataDto.withEmployment(validEmploymentDto.withEmploymentStatus(EmploymentStatus.UNEMPLOYED)),
                validScoringDataDto.withEmployment(validEmploymentDto.withSalary(validScoringDataDto.amount()
                        .divide(BigDecimal.valueOf(25), MathContext.DECIMAL64))),
                validScoringDataDto.withBirthdate(LocalDate.now().minusYears(19)),
                validScoringDataDto.withBirthdate(LocalDate.now().minusYears(66)),
                validScoringDataDto.withEmployment(validEmploymentDto.withWorkExperienceTotal(17)),
                validScoringDataDto.withEmployment(validEmploymentDto.withWorkExperienceCurrent(2)));
    }

    @Test
    void givenValidScoringDataDto_whenValidateScoring_thenNotThrowException() {
        ScoringDataDto validScoringDataDto = TestUtils.getValidScoringDataDto();
        assertDoesNotThrow(() -> scoringService.validateScoring(validScoringDataDto));
    }

    @Test
    void givenValidScoringDataDto_whenCalculateCredit_thenReturnCreditDto() {
        CreditDto validCreditDto = TestUtils.getValidCreditDto();
        BigDecimal scoredRate = validCreditDto.rate();
        BigDecimal baseRate = scoredRate.add(BigDecimal.TWO);
        BigDecimal prescoredRate = baseRate.add(BigDecimal.ONE);
        BigDecimal requestedAmount = validCreditDto.amount();
        BigDecimal monthlyPayment = validCreditDto.monthlyPayment();
        BigDecimal totalAmount = validCreditDto.psk();
        List<PaymentScheduleElementDto> payments = validCreditDto.paymentSchedule();
        ScoringDataDto validScoringDataDto = TestUtils.getValidScoringDataDto();

        when(rateConfig.getBaseRate()).thenReturn(baseRate);
        doNothing().when(scoringService).validateScoring(any(ScoringDataDto.class));
        when(rateAdjustmentService.prescoreRate(any(BigDecimal.class), anyBoolean(), anyBoolean()))
                .thenReturn(prescoredRate);
        when(rateAdjustmentService.scoreRate(any(BigDecimal.class), any(ScoringDataDto.class))).thenReturn(scoredRate);
        when(annuityCalculatorService.calculateRequestedAmount(any(BigDecimal.class), anyBoolean()))
                .thenReturn(requestedAmount);
        when(annuityCalculatorService.calculateMonthlyPayment(any(BigDecimal.class), anyInt(), any(BigDecimal.class)))
                .thenReturn(monthlyPayment);
        when(annuityCalculatorService.calculateTotalAmount(any(BigDecimal.class), anyInt())).thenReturn(totalAmount);
        when(annuityCalculatorService.generatePaymentsSchedule(anyInt(), any(BigDecimal.class), any(BigDecimal.class),
                any(BigDecimal.class))).thenReturn(payments);
        CreditDto returnedCreditDto = scoringService.calculateCredit(validScoringDataDto);
        assertEquals(validCreditDto, returnedCreditDto);
    }
}