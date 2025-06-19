package ru.neoflex.bank.calculator.service;

import ru.neoflex.bank.calculator.model.dto.LoanOfferDto;
import ru.neoflex.bank.calculator.model.dto.LoanStatementRequestDto;

import java.util.List;

public interface LoanOfferCalculatorService {
    List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatementRequestDto);
}
