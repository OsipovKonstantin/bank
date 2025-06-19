package ru.neoflex.bank.calculator.service;

import ru.neoflex.bank.calculator.model.dto.ScoringDataDto;

import java.math.BigDecimal;

public interface RateAdjustmentService {
    BigDecimal prescoreRate(BigDecimal rate, boolean isInsuranceEnabled, boolean isSalaryClient);

    BigDecimal scoreRate(BigDecimal rate, ScoringDataDto scoringDataDto);
}
