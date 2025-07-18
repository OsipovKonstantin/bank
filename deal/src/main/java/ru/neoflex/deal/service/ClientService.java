package ru.neoflex.deal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.deal.logging.Logging;
import ru.neoflex.deal.mapper.ClientMapper;
import ru.neoflex.deal.mapper.EmploymentDataMapper;
import ru.neoflex.deal.mapper.PassportDataMapper;
import ru.neoflex.deal.model.dto.FinishRegistrationRequestDto;
import ru.neoflex.deal.model.entity.Client;
import ru.neoflex.deal.model.jsonb.EmploymentData;
import ru.neoflex.deal.model.jsonb.PassportData;
import ru.neoflex.deal.repository.ClientRepository;

@Logging
@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {
    private final PassportDataMapper passportDataMapper;
    private final EmploymentDataMapper employmentDataMapper;
    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    public Client refreshClient(Client client, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        PassportData passportData = client.getPassport().getPassportData();
        PassportData newPassportData = passportDataMapper.toFullPassportData(passportData, finishRegistrationRequestDto);
        EmploymentData employmentData = employmentDataMapper.toEmploymentData(finishRegistrationRequestDto.employment());
        Client fullClient = clientMapper.toFullClient(client, finishRegistrationRequestDto, newPassportData, employmentData);
        return clientRepository.save(fullClient);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }
}
