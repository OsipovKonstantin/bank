package ru.neoflex.bank.calculator.model.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record LoanOfferDto(
        UUID statementId,
        BigDecimal requestedAmount,
        BigDecimal totalAmount,
        Integer term,
        BigDecimal monthlyPayment,
        BigDecimal rate,
        Boolean isInsuranceEnabled,
        Boolean isSalaryClient
) {}
