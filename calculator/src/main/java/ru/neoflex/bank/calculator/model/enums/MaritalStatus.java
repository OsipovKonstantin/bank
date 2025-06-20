package ru.neoflex.bank.calculator.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Семейное положение", enumAsRef = true)
public enum MaritalStatus {
    SINGLE,
    MARRIED,
    DIVORCED,
    WIDOWED
}
