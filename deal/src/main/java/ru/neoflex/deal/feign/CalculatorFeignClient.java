package ru.neoflex.deal.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.neoflex.deal.configuration.CalculatorFeignClientConfiguration;
import ru.neoflex.deal.model.dto.CreditDto;
import ru.neoflex.deal.model.dto.LoanOfferDto;
import ru.neoflex.deal.model.dto.LoanStatementRequestDto;
import ru.neoflex.deal.model.dto.ScoringDataDto;

import java.util.List;

@FeignClient(name = "calculator", url = "${calculator.url}", configuration = CalculatorFeignClientConfiguration.class)
public interface CalculatorFeignClient {

    @PostMapping(value = "/offers")
    List<LoanOfferDto> calculateLoanOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping(value = "/calc")
    CreditDto calculateCredit(@RequestBody ScoringDataDto scoringDataDto);
}
