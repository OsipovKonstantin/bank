package ru.neoflex.bank.calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.bank.calculator.configuration.RateConfig;
import ru.neoflex.bank.calculator.exception.ScoringRejectException;
import ru.neoflex.bank.common.model.dto.CreditDto;
import ru.neoflex.bank.common.model.dto.PaymentScheduleElementDto;
import ru.neoflex.bank.common.model.dto.ScoringDataDto;
import ru.neoflex.bank.common.model.enums.EmploymentStatus;
import ru.neoflex.bank.common.util.DateTimeUtils;
import ru.neoflex.bank.common.logging.Logging;

import java.math.BigDecimal;
import java.util.List;

@Logging
@Service
@RequiredArgsConstructor
public class ScoringService {
    private final AnnuityCalculatorService annuityCalculatorService;
    private final RateAdjustmentService rateAdjustmentService;
    private final RateConfig rateConfig;

    public CreditDto calculateCredit(ScoringDataDto scoringDataDto) {
        validateScoring(scoringDataDto);

        Boolean isInsuranceEnabled = scoringDataDto.isInsuranceEnabled();
        Boolean isSalaryClient = scoringDataDto.isSalaryClient();
        BigDecimal rate = rateAdjustmentService.prescoreRate(rateConfig.getBaseRate(), isInsuranceEnabled,
                isSalaryClient);
        rate = rateAdjustmentService.scoreRate(rate, scoringDataDto);

        Integer term = scoringDataDto.term();
        BigDecimal requestedAmount = annuityCalculatorService.calculateRequestedAmount(scoringDataDto.amount(), isInsuranceEnabled);
        BigDecimal monthlyPayment = annuityCalculatorService.calculateMonthlyPayment(requestedAmount, term, rate);
        BigDecimal totalAmount = annuityCalculatorService.calculateTotalAmount(monthlyPayment, term);
        List<PaymentScheduleElementDto> listOfPayments = annuityCalculatorService.generatePaymentsSchedule(term,
                monthlyPayment, requestedAmount, rate);
        return CreditDto.builder()
                .amount(requestedAmount)
                .term(term)
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .psk(totalAmount)
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .paymentSchedule(listOfPayments).build();
    }

    public void validateScoring(ScoringDataDto scoringDataDto) {
        if (scoringDataDto.employment().employmentStatus() == EmploymentStatus.UNEMPLOYED) {
            throw new ScoringRejectException("Заявка отклонена: статус занятости — безработный");
        }
        BigDecimal requestedAmount = scoringDataDto.amount();
        if (requestedAmount.compareTo(scoringDataDto.employment().salary().multiply(BigDecimal.valueOf(24))) > 0) {
            throw new ScoringRejectException("Заявка отклонена: сумма займа больше, чем 24 зарплаты");
        }
        int age = DateTimeUtils.calculateAge(scoringDataDto.birthdate());
        if (age < 20 || age > 65) {
            throw new ScoringRejectException("Заявка отклонена: возраст вне диапазона 20-65 лет");
        }
        if (scoringDataDto.employment().workExperienceTotal() < 18) {
            throw new ScoringRejectException("Заявка отклонена: общий стаж работы менее 18 месяцев");
        }
        if (scoringDataDto.employment().workExperienceCurrent() < 3) {
            throw new ScoringRejectException("Заявка отклонена: текущий стаж работы менее 3 месяцев");
        }
    }
}