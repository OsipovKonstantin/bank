package ru.neoflex.bank.calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.bank.calculator.configuration.RateConfig;
import ru.neoflex.bank.calculator.model.dto.LoanOfferDto;
import ru.neoflex.bank.calculator.model.dto.LoanStatementRequestDto;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class LoanOfferCalculatorService {
    private final RateConfig rateConfig;
    private final RateAdjustmentService rateAdjustmentService;
    private final AnnuityCalculatorService annuityCalculatorService;

    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        UUID statementId = UUID.randomUUID();
        return Stream.of(
                calculateLoanOffer(statementId, true, true, loanStatementRequestDto),
                calculateLoanOffer(statementId, true, false, loanStatementRequestDto),
                calculateLoanOffer(statementId, false, true, loanStatementRequestDto),
                calculateLoanOffer(statementId, false, false, loanStatementRequestDto)
        ).sorted(Comparator.comparing(LoanOfferDto::rate).reversed()).toList();
    }

    public LoanOfferDto calculateLoanOffer(UUID statementId, boolean isInsuranceEnabled, boolean isSalaryClient,
                                            LoanStatementRequestDto loanStatementRequestDto) {
        BigDecimal rate = rateAdjustmentService.prescoreRate(rateConfig.getBaseRate(), isInsuranceEnabled,
                isSalaryClient);

        Integer term = loanStatementRequestDto.term();
        BigDecimal requestedAmount = annuityCalculatorService.calculateRequestedAmount(loanStatementRequestDto.amount(), isInsuranceEnabled);
        BigDecimal monthlyPayment = annuityCalculatorService.calculateMonthlyPayment(requestedAmount, term, rate);
        BigDecimal totalAmount = annuityCalculatorService.calculateTotalAmount(monthlyPayment, term);

        return new LoanOfferDto(statementId,
                requestedAmount,
                totalAmount,
                term,
                monthlyPayment,
                rate,
                isInsuranceEnabled,
                isSalaryClient);
    }
}