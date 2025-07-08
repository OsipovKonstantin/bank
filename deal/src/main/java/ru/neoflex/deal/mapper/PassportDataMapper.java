package ru.neoflex.deal.mapper;

import org.mapstruct.Mapper;
import ru.neoflex.deal.model.jsonb.PassportData;

@Mapper(componentModel = "spring")
public interface PassportDataMapper {
    PassportData toPassportData(String series, String number);
}
