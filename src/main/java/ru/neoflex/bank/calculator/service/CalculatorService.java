package ru.neoflex.bank.calculator.service;

import ru.neoflex.bank.calculator.model.dto.CreditDto;
import ru.neoflex.bank.calculator.model.dto.LoanOfferDto;
import ru.neoflex.bank.calculator.model.dto.LoanStatementRequestDto;
import ru.neoflex.bank.calculator.model.dto.ScoringDataDto;

import java.util.List;

public interface CalculatorService {
    List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatementRequestDto);

    CreditDto calculateCredit(ScoringDataDto scoringDataDto);
}
