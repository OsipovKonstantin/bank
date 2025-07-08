package ru.neoflex.deal.model.jsonb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.With;

import java.time.LocalDate;

@With
@Builder
public record PassportData(
        String series,
        String number,
        @JsonProperty("issue_branch")
        String issueBranch,
        @JsonProperty("issue_date")
        LocalDate issueDate
) {}
