package ru.neoflex.deal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.deal.feign.CalculatorFeignClient;
import ru.neoflex.deal.logging.Logging;
import ru.neoflex.deal.mapper.*;
import ru.neoflex.deal.model.dto.*;
import ru.neoflex.deal.model.entity.Client;
import ru.neoflex.deal.model.entity.Credit;
import ru.neoflex.deal.model.entity.Statement;
import ru.neoflex.deal.model.enums.ChangeType;
import ru.neoflex.deal.model.enums.CreditStatus;
import ru.neoflex.deal.model.enums.Status;
import ru.neoflex.deal.model.jsonb.AppliedOffer;
import ru.neoflex.deal.model.jsonb.PassportData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Logging
@Service
@Transactional
@RequiredArgsConstructor
public class DealService {
    private final ClientMapper clientMapper;
    private final PassportDataMapper passportDataMapper;
    private final StatementMapper statementMapper;
    private final CalculatorFeignClient calculatorFeignClient;
    private final OfferMapper offerMapper;
    private final ScoringDataMapper scoringDataMapper;
    private final CreditMapper creditMapper;
    private final ClientService clientService;
    private final StatementService statementService;

    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        PassportData passportData = passportDataMapper.toPassportData(loanStatementRequestDto.passportSeries(),
                loanStatementRequestDto.passportNumber());
        Client client = clientMapper.toClient(loanStatementRequestDto, passportData);
        Client savedClient = clientService.save(client);

        Statement statement = statementMapper.toStatement(savedClient, LocalDateTime.now(), new ArrayList<>());
        statementService.setStatus(statement, Status.PREAPPROVAL, ChangeType.AUTOMATIC);
        Statement savedStatement = statementService.save(statement);

        List<LoanOfferDto> offers = calculatorFeignClient.calculateLoanOffers(loanStatementRequestDto);
        List<LoanOfferDto> updatedOffers = offers.stream().map(o -> o.withStatementId(savedStatement.getId()))
                .toList();
        return updatedOffers;
    }

    public void saveSelectedOffer(LoanOfferDto loanOfferDto) {
        Statement statement = statementService.findStatementById(loanOfferDto.statementId());
        statementService.setStatus(statement, Status.APPROVED, ChangeType.AUTOMATIC);
        AppliedOffer appliedOffer = offerMapper.toAppliedOffer(loanOfferDto);
        statement.setAppliedOffer(appliedOffer);
    }

    public void finishRegistrationAndCalculateCredit(FinishRegistrationRequestDto finishRegistrationRequestDto,
                                                     String statementId) {
        UUID uuidStatementId = UUID.fromString(statementId);
        Statement statement = statementService.findStatementById(uuidStatementId);
        Client client = statement.getClient();
        Client refreshedClient = clientService.refreshClient(client, finishRegistrationRequestDto);
        AppliedOffer appliedOffer = statement.getAppliedOffer();
        ScoringDataDto scoringDataDto = scoringDataMapper.toScoringDataDto(finishRegistrationRequestDto, refreshedClient,
                appliedOffer);
        CreditDto creditDto = calculatorFeignClient.calculateCredit(scoringDataDto);
        Credit credit = creditMapper.toCredit(creditDto, CreditStatus.CALCULATED);
        statement.setCredit(credit);
        statementService.setStatus(statement, Status.CC_APPROVED, ChangeType.AUTOMATIC);
    }
}
