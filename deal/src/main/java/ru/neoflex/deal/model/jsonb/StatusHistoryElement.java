package ru.neoflex.deal.model.jsonb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.With;
import ru.neoflex.deal.model.enums.ChangeType;
import ru.neoflex.deal.model.enums.Status;

import java.time.LocalDateTime;

import static ru.neoflex.deal.util.DateTimeUtils.DATE_TIME_PATTERN;

@With
@Builder
public record StatusHistoryElement(
        Status status,
        @JsonFormat(pattern = DATE_TIME_PATTERN)
        LocalDateTime time,
        @JsonProperty("change_type")
        ChangeType changeType
) {
}