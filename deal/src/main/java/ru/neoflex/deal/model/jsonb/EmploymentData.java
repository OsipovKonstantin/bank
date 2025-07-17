package ru.neoflex.deal.model.jsonb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.With;
import ru.neoflex.deal.model.enums.EmploymentPosition;
import ru.neoflex.deal.model.enums.EmploymentStatus;

import java.math.BigDecimal;

@With
@Builder
public record EmploymentData(
        EmploymentStatus status,
        @JsonProperty("employer_inn")
        String employerInn,
        BigDecimal salary,
        EmploymentPosition position,
        @JsonProperty("work_experience_total")
        Integer workExperienceTotal,
        @JsonProperty("work_experience_current")
        Integer workExperienceCurrent
) {
}
