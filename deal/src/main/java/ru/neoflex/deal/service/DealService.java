package ru.neoflex.deal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.deal.exception.NotFoundException;
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
import ru.neoflex.deal.model.jsonb.EmploymentData;
import ru.neoflex.deal.model.jsonb.PassportData;
import ru.neoflex.deal.model.jsonb.StatusHistoryElement;
import ru.neoflex.deal.repository.ClientRepository;
import ru.neoflex.deal.repository.StatementRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Logging
@Service
@RequiredArgsConstructor
@Transactional
public class DealService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PassportDataMapper passportDataMapper;
    private final StatementMapper statementMapper;
    private final StatementRepository statementRepository;
    private final CalculatorFeignClient calculatorFeignClient;
    private final OfferMapper offerMapper;
    private final ScoringDataMapper scoringDataMapper;
    private final CreditMapper creditMapper;
    private final EmploymentDataMapper employmentDataMapper;

    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        PassportData passportData = passportDataMapper.toPassportData(loanStatementRequestDto.passportSeries(),
                loanStatementRequestDto.passportNumber());
        Client client = clientMapper.toClient(loanStatementRequestDto, passportData);
        clientRepository.save(client);

        Statement statement = statementMapper.toStatement(client, LocalDateTime.now(), new ArrayList<>());
        setStatus(statement, Status.PREAPPROVAL, ChangeType.AUTOMATIC);
        Statement savedStatement = statementRepository.save(statement);

        List<LoanOfferDto> offers = calculatorFeignClient.calculateLoanOffers(loanStatementRequestDto);
        List<LoanOfferDto> updatedOffers = offers.stream().map(o -> o.withStatementId(savedStatement.getId()))
                .toList();
        return updatedOffers;
    }

    public void saveSelectedOffer(LoanOfferDto loanOfferDto) {
        Statement statement = findStatementById(loanOfferDto.statementId());
        setStatus(statement, Status.APPROVED, ChangeType.AUTOMATIC);
        AppliedOffer appliedOffer = offerMapper.toAppliedOffer(loanOfferDto);
        statement.setAppliedOffer(appliedOffer);
    }

    public Statement findStatementById(UUID uuid) {
        return statementRepository.findById(uuid).orElseThrow(
                () -> new NotFoundException(String.format("Заявка с id %s не найдена", uuid)));
    }

    public void setStatus(Statement statement, Status status, ChangeType changeType) {
        statement.setStatus(status);
        StatusHistoryElement statusHistoryElement = StatusHistoryElement.builder()
                .status(status)
                .changeType(changeType)
                .time(LocalDateTime.now())
                .build();
        List<StatusHistoryElement> statusHistory = statement.getStatusHistory();
        statusHistory.add(statusHistoryElement);
    }

    public void finishRegistrationAndCalculateCredit(FinishRegistrationRequestDto finishRegistrationRequestDto,
                                                     String statementId) {
        UUID uuidStatementId = UUID.fromString(statementId);
        Statement statement = findStatementById(uuidStatementId);
        Client client = statement.getClient();
        Client refreshedClient = refreshClient(client, finishRegistrationRequestDto);
        AppliedOffer appliedOffer = statement.getAppliedOffer();
        ScoringDataDto scoringDataDto = scoringDataMapper.toScoringDataDto(finishRegistrationRequestDto, refreshedClient,
                appliedOffer);
        CreditDto creditDto = calculatorFeignClient.calculateCredit(scoringDataDto);
        Credit credit = creditMapper.toCredit(creditDto, CreditStatus.CALCULATED);
        statement.setCredit(credit);
        setStatus(statement, Status.CC_APPROVED, ChangeType.AUTOMATIC);
    }

    public Client refreshClient(Client client, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        PassportData passportData = client.getPassport().getPassportData();
        PassportData newPassportData = passportDataMapper.toFullPassportData(passportData, finishRegistrationRequestDto);
        EmploymentData employmentData = employmentDataMapper.toEmploymentData(finishRegistrationRequestDto.employment());
        Client fullClient = clientMapper.toFullClient(client, finishRegistrationRequestDto, newPassportData, employmentData);
        return clientRepository.save(fullClient);
    }
}
