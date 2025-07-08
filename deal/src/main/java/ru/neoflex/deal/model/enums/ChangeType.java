package ru.neoflex.deal.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Способ изменения статуса заявки")
public enum ChangeType {
    AUTOMATIC,
    MANUAL
}
