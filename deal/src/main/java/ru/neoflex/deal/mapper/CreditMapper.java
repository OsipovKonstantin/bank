package ru.neoflex.deal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.deal.model.dto.CreditDto;
import ru.neoflex.deal.model.entity.Credit;
import ru.neoflex.deal.model.enums.CreditStatus;

@Mapper(componentModel = "spring")
public interface CreditMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "amount", source = "creditDto.amount")
    @Mapping(target = "term", source = "creditDto.term")
    @Mapping(target = "monthlyPayment", source = "creditDto.monthlyPayment")
    @Mapping(target = "rate", source = "creditDto.rate")
    @Mapping(target = "psk", source = "creditDto.psk")
    @Mapping(target = "paymentSchedule", source = "creditDto.paymentSchedule")
    @Mapping(target = "isInsuranceEnabled", source = "creditDto.isInsuranceEnabled")
    @Mapping(target = "isSalaryClient", source = "creditDto.isSalaryClient")
    @Mapping(target = "creditStatus", source = "creditStatus")
    Credit toCredit(CreditDto creditDto, CreditStatus creditStatus);
}
