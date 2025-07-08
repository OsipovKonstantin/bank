package ru.neoflex.deal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.deal.model.dto.LoanStatementRequestDto;
import ru.neoflex.deal.model.entity.Client;
import ru.neoflex.deal.model.entity.Passport;
import ru.neoflex.deal.model.jsonb.PassportData;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "id")
    @Mapping(target = "lastName", source = "loanStatement.lastName")
    @Mapping(target = "firstName", source = "loanStatement.firstName")
    @Mapping(target = "middleName", source = "loanStatement.middleName")
    @Mapping(target = "birthdate", source = "loanStatement.birthdate")
    @Mapping(target = "email",source = "loanStatement.email")
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "maritalStatus", ignore = true)
    @Mapping(target = "dependentAmount", ignore = true)
    @Mapping(target = "passport", source = "passport")
    @Mapping(target = "employment", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    Client toClient(LoanStatementRequestDto loanStatement, Passport passport);
}