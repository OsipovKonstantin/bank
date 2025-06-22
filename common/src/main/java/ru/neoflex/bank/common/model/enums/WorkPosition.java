package ru.neoflex.bank.common.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Должность", enumAsRef = true)
public enum WorkPosition {
    SPECIALIST,
    MIDDLE_MANAGER,
    TOP_MANAGER
}
