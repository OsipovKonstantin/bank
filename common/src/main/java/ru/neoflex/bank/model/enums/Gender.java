package ru.neoflex.bank.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Пол", enumAsRef = true)
public enum Gender {
    FEMALE,
    MALE,
    NON_BINARY
}
