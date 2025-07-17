package ru.neoflex.deal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.deal.model.dto.EmploymentDto;
import ru.neoflex.deal.model.entity.Employment;
import ru.neoflex.deal.model.jsonb.EmploymentData;

@Mapper(componentModel = "spring")
public interface EmploymentDataMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employmentData", source = "employmentData")
    Employment toEmployment(EmploymentData employmentData);

    @Mapping(target = "status", source = "employmentDto.employmentStatus")
    @Mapping(target = "employerInn", source = "employmentDto.EmployerINN")
    @Mapping(target = "salary", source = "employmentDto.salary")
    @Mapping(target = "position", source = "employmentDto.position")
    @Mapping(target = "workExperienceTotal", source = "employmentDto.workExperienceTotal")
    @Mapping(target = "workExperienceCurrent", source = "employmentDto.workExperienceCurrent")
    EmploymentData toEmploymentData(EmploymentDto employmentDto);
}
