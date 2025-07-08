package ru.neoflex.deal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.deal.mapper.ClientMapper;
import ru.neoflex.deal.mapper.PassportDataMapper;
import ru.neoflex.deal.mapper.PassportMapper;
import ru.neoflex.deal.mapper.StatementMapper;
import ru.neoflex.deal.model.dto.LoanOfferDto;
import ru.neoflex.deal.model.dto.LoanStatementRequestDto;
import ru.neoflex.deal.model.entity.Client;
import ru.neoflex.deal.model.entity.Passport;
import ru.neoflex.deal.model.entity.Statement;
import ru.neoflex.deal.model.enums.Status;
import ru.neoflex.deal.model.jsonb.PassportData;
import ru.neoflex.deal.repository.ClientRepository;
import ru.neoflex.deal.repository.StatementRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DealService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PassportMapper passportMapper;
    private final PassportDataMapper passportDataMapper;
    private final StatementMapper statementMapper;
    private final StatementRepository statementRepository;

    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatementRequestDto, Status status) {
        PassportData passportData = passportDataMapper.toPassportData(loanStatementRequestDto.passportSeries(),
                loanStatementRequestDto.passportNumber());
        Passport passport = passportMapper.toPassport(passportData);
        Client client = clientMapper.toClient(loanStatementRequestDto, passport);
        clientRepository.save(client);

        Statement statement = statementMapper.toStatement(client, LocalDateTime.now(), new ArrayList<>());
        statementRepository.save(statement);
        return null;
    }
}
