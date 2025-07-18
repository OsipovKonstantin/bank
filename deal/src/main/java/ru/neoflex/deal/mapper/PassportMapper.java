package ru.neoflex.deal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.deal.model.entity.Passport;
import ru.neoflex.deal.model.jsonb.PassportData;

@Mapper(componentModel = "spring")
public interface PassportMapper {
    @Mapping(target = "id", ignore = true)
    Passport toPassport(PassportData passportData);
}
