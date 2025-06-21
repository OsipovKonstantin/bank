package ru.neoflex.bank.calculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.bank.model.dto.CreditDto;
import ru.neoflex.bank.model.dto.LoanOfferDto;
import ru.neoflex.bank.model.dto.LoanStatementRequestDto;
import ru.neoflex.bank.model.dto.ScoringDataDto;
import ru.neoflex.bank.calculator.service.LoanOfferCalculatorService;
import ru.neoflex.bank.calculator.service.ScoringService;

import java.util.List;

@RestController
@RequestMapping("/calculator")
@RequiredArgsConstructor
@Tag(name = "Calculator", description = "API для расчетов кредитных предложений и кредитных расчетов")
public class CalculatorController {
    private final LoanOfferCalculatorService loanOfferCalculatorService;
    private final ScoringService scoringService;

    @Operation(summary = "Расчет кредитных предложений",
            description = "Вычисляет список кредитных предложений на основе исходных данных клиента")
    @PostMapping("/offers")
    public List<LoanOfferDto> calculateLoanOffers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {
        return loanOfferCalculatorService.calculateLoanOffers(loanStatementRequestDto);
    }

    @Operation(summary = "Финальный расчет кредита",
            description = "Проводит скоринг клиента и возвращает итоговые параметры кредита")
    @PostMapping("/calc")
    public CreditDto calculateCredit(@RequestBody @Valid ScoringDataDto scoringDataDto) {
        return scoringService.calculateCredit(scoringDataDto);
    }
}
