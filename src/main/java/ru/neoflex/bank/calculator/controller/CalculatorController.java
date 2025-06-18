package ru.neoflex.bank.calculator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.bank.calculator.model.dto.CreditDto;
import ru.neoflex.bank.calculator.model.dto.LoanOfferDto;
import ru.neoflex.bank.calculator.model.dto.LoanStatementRequestDto;
import ru.neoflex.bank.calculator.model.dto.ScoringDataDto;
import ru.neoflex.bank.calculator.service.CalculatorService;

import java.util.List;

@RestController
@RequestMapping("/calculator")
@RequiredArgsConstructor
public class CalculatorController {
    private final CalculatorService calculatorService;

    @PostMapping("/offers")
    public List<LoanOfferDto> calculateLoanOffers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {
        return calculatorService.calculateLoanOffers(loanStatementRequestDto);
    }

    @PostMapping("/calc")
    public CreditDto calculateCredit(@RequestBody @Valid ScoringDataDto scoringDataDto) {
        return calculatorService.calculateCredit(scoringDataDto);
    }
}
