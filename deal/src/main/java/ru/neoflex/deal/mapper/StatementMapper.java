package ru.neoflex.deal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.deal.model.dto.StatementStatusHistoryDto;
import ru.neoflex.deal.model.entity.Client;
import ru.neoflex.deal.model.entity.Statement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StatementMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", source = "client")
    @Mapping(target = "credit", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "creationDate", source = "creationDate")
    @Mapping(target = "appliedOffer", ignore = true)
    @Mapping(target = "sesCode", ignore = true)
    @Mapping(target = "statusHistory" ,source = "statusHistory")
    Statement toStatement(Client client, LocalDateTime creationDate, List<StatementStatusHistoryDto> statusHistory);
}
