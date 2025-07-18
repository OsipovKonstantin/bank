package ru.neoflex.deal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.deal.model.dto.FinishRegistrationRequestDto;
import ru.neoflex.deal.model.dto.LoanStatementRequestDto;
import ru.neoflex.deal.model.entity.Client;
import ru.neoflex.deal.model.jsonb.EmploymentData;
import ru.neoflex.deal.model.jsonb.PassportData;

@Mapper(componentModel = "spring", uses = {PassportMapper.class, EmploymentDataMapper.class})
public interface ClientMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastName", source = "loanStatement.lastName")
    @Mapping(target = "firstName", source = "loanStatement.firstName")
    @Mapping(target = "middleName", source = "loanStatement.middleName")
    @Mapping(target = "birthdate", source = "loanStatement.birthdate")
    @Mapping(target = "email", source = "loanStatement.email")
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "maritalStatus", ignore = true)
    @Mapping(target = "dependentAmount", ignore = true)
    @Mapping(target = "passport", source = "passportData")
    @Mapping(target = "employment", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    Client toClient(LoanStatementRequestDto loanStatement, PassportData passportData);

    @Mapping(target = "id", source = "client.id")
    @Mapping(target = "lastName", source = "client.lastName")
    @Mapping(target = "firstName", source = "client.firstName")
    @Mapping(target = "middleName", source = "client.middleName")
    @Mapping(target = "birthdate", source = "client.birthdate")
    @Mapping(target = "email", source = "client.email")
    @Mapping(target = "gender", source = "finishRegistrationRequestDto.gender")
    @Mapping(target = "maritalStatus", source = "finishRegistrationRequestDto.maritalStatus")
    @Mapping(target = "dependentAmount", source = "finishRegistrationRequestDto.dependentAmount")
    @Mapping(target = "passport", source = "passportData")
    @Mapping(target = "employment", source = "employmentData")
    @Mapping(target = "accountNumber", source = "finishRegistrationRequestDto.accountNumber")
    Client toFullClient(Client client, FinishRegistrationRequestDto finishRegistrationRequestDto,
                        PassportData passportData, EmploymentData employmentData);
}