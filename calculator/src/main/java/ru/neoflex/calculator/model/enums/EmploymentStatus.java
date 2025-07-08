package ru.neoflex.calculator.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Трудовой статус", enumAsRef = true)
public enum EmploymentStatus {
    UNEMPLOYED,
    SELF_EMPLOYED,
    EMPLOYED,
    BUSINESS_OWNER
}