package ru.neoflex.calculator.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Пол", enumAsRef = true)
public enum Gender {
    MALE,
    FEMALE,
    NON_BINARY
}
