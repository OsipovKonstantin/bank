package ru.neoflex.deal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.deal.model.dto.LoanOfferDto;
import ru.neoflex.deal.model.jsonb.AppliedOffer;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    @Mapping(target = "statementId", source = "loanOfferDto.statementId")
    @Mapping(target = "requestedAmount", source = "loanOfferDto.requestedAmount")
    @Mapping(target = "totalAmount", source = "loanOfferDto.totalAmount")
    @Mapping(target = "term", source = "loanOfferDto.term")
    @Mapping(target = "monthlyPayment", source = "loanOfferDto.monthlyPayment")
    @Mapping(target = "rate", source = "loanOfferDto.rate")
    @Mapping(target = "isInsuranceEnabled", source = "loanOfferDto.isInsuranceEnabled")
    @Mapping(target = "isSalaryClient", source = "loanOfferDto.isSalaryClient")
    AppliedOffer toAppliedOffer(LoanOfferDto loanOfferDto);
}
