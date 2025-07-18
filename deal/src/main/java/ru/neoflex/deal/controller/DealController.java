package ru.neoflex.deal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.deal.model.dto.FinishRegistrationRequestDto;
import ru.neoflex.deal.model.dto.LoanOfferDto;
import ru.neoflex.deal.model.dto.LoanStatementRequestDto;
import ru.neoflex.deal.service.DealService;

import java.util.List;

@Tag(name = "Deal", description = "API для микросервиса Сделка")
@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class DealController {
    private final DealService dealService;

    @Operation(summary = "Расчёт возможных условий кредита", description = "На основе входных параметров расчитывается " +
            "4 кредитных предложения")
    @PostMapping("/statement")
    public List<LoanOfferDto> calculateLoanOffers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {
        return dealService.calculateLoanOffers(loanStatementRequestDto);
    }

    @Operation(summary = "Выбор одного из кредитных предложений", description = "Выбранное предложение сохраняется " +
            "в заявку клиента")
    @PostMapping("/offer/select")
    public void saveSelectedOffer(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        dealService.saveSelectedOffer(loanOfferDto);
    }

    @Operation(summary = "Завершение регистрации и полный подсчёт кредита", description = "Финальный расчет условий " +
            "кредита (скоринг) на основе расширенных входных данных. Сохранение уточненных данных о клиенте." +
            "Обновление заявки клиента с учетом финальных расчетов по кредиту")
    @PostMapping("/calculate/{statementId}")
    public void finishRegistrationAndCalculateCredit(@RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto,
                                                     @PathVariable String statementId) {
        dealService.finishRegistrationAndCalculateCredit(finishRegistrationRequestDto, statementId);
    }
}
