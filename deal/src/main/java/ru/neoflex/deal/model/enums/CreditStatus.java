package ru.neoflex.deal.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Статус выдаваемого кредита", enumAsRef = true)
public enum CreditStatus {
    CALCULATED,
    ISSUED
}
