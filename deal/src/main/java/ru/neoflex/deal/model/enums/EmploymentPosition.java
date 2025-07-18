package ru.neoflex.deal.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Должность", enumAsRef = true)
public enum EmploymentPosition {
    WORKER,
    MID_MANAGER,
    TOP_MANAGER,
    OWNER
}
