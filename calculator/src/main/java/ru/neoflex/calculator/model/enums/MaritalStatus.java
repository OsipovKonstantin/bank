package ru.neoflex.calculator.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Семейное положение", enumAsRef = true)
public enum MaritalStatus {
    MARRIED,
    DIVORCED,
    SINGLE,
    WIDOW_WIDOWER
}
