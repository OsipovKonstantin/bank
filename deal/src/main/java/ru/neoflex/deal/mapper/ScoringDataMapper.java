package ru.neoflex.deal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.deal.model.dto.FinishRegistrationRequestDto;
import ru.neoflex.deal.model.dto.ScoringDataDto;
import ru.neoflex.deal.model.entity.Client;
import ru.neoflex.deal.model.jsonb.AppliedOffer;

@Mapper(componentModel = "spring")
public interface ScoringDataMapper {

    @Mapping(target = "amount", source = "appliedOffer.requestedAmount")
    @Mapping(target = "term", source = "appliedOffer.term")
    @Mapping(target = "firstName", source = "client.firstName")
    @Mapping(target = "lastName", source = "client.lastName")
    @Mapping(target = "middleName", source = "client.middleName")
    @Mapping(target = "gender", source = "finishRegistrationRequestDto.gender")
    @Mapping(target = "birthdate", source = "client.birthdate")
    @Mapping(target = "passportSeries", source = "client.passport.passportData.series")
    @Mapping(target = "passportNumber", source = "client.passport.passportData.number")
    @Mapping(target = "passportIssueDate", source = "finishRegistrationRequestDto.passportIssueDate")
    @Mapping(target = "passportIssueBranch", source = "finishRegistrationRequestDto.passportIssueBranch")
    @Mapping(target = "maritalStatus", source = "finishRegistrationRequestDto.maritalStatus")
    @Mapping(target = "dependentAmount", source = "finishRegistrationRequestDto.dependentAmount")
    @Mapping(target = "employment", source = "finishRegistrationRequestDto.employment")
    @Mapping(target = "accountNumber", source = "finishRegistrationRequestDto.accountNumber")
    @Mapping(target = "isInsuranceEnabled", source = "appliedOffer.isInsuranceEnabled")
    @Mapping(target = "isSalaryClient", source = "appliedOffer.isSalaryClient")
    ScoringDataDto toScoringDataDto(FinishRegistrationRequestDto finishRegistrationRequestDto, Client client,
                                    AppliedOffer appliedOffer);
}
