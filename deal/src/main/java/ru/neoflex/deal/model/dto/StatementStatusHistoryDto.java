package ru.neoflex.deal.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.With;
import ru.neoflex.deal.model.enums.ChangeType;
import ru.neoflex.deal.model.enums.Status;

import java.time.LocalDateTime;

import static ru.neoflex.deal.util.DateTimeUtils.DATE_TIME_PATTERN;

@With
@Builder
@Schema(description = "Элемент истории статусов заявки")
public record StatementStatusHistoryDto(
        @Schema(description = "Статус заявки", example = "APPROVED")
        Status status,
        @Schema(description = "Дата и время изменения статуса заявки", example = "2025-01-01 00:00:00")
        @JsonFormat(pattern = DATE_TIME_PATTERN)
        LocalDateTime time,
        @Schema(description = "Способ изменения статуса заявки", example = "MANUAL")
        ChangeType changeType
) {}
