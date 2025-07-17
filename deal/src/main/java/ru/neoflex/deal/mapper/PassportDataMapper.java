package ru.neoflex.deal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.deal.model.dto.FinishRegistrationRequestDto;
import ru.neoflex.deal.model.jsonb.PassportData;

@Mapper(componentModel = "spring")
public interface PassportDataMapper {
    PassportData toPassportData(String series, String number);

    @Mapping(target = "series", source = "passportData.series")
    @Mapping(target = "number", source = "passportData.number")
    @Mapping(target = "issueBranch", source = "finishRegistrationRequestDto.passportIssueBranch")
    @Mapping(target = "issueDate", source = "finishRegistrationRequestDto.passportIssueDate")
    PassportData toFullPassportData(PassportData passportData, FinishRegistrationRequestDto finishRegistrationRequestDto);
}
