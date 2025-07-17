package ru.neoflex.deal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.deal.model.dto.FinishRegistrationRequestDto;
import ru.neoflex.deal.model.dto.LoanOfferDto;
import ru.neoflex.deal.model.dto.LoanStatementRequestDto;
import ru.neoflex.deal.service.DealService;

import java.util.List;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class DealController {
    private final DealService dealService;

    @PostMapping("/statement")
    public List<LoanOfferDto> calculateLoanOffers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {
        return dealService.calculateLoanOffers(loanStatementRequestDto);
    }

    @PostMapping("/offer/select")
    public void saveSelectedOffer(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        dealService.saveSelectedOffer(loanOfferDto);
    }

    @PostMapping("/calculate/{statementId}")
    public void finishRegistrationAndCalculateCredit(@RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto,
                                                     @PathVariable String statementId) {
        dealService.finishRegistrationAndCalculateCredit(finishRegistrationRequestDto, statementId);
    }
}
