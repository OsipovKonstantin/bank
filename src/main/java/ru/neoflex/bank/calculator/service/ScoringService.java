package ru.neoflex.bank.calculator.service;

import ru.neoflex.bank.calculator.model.dto.CreditDto;
import ru.neoflex.bank.calculator.model.dto.ScoringDataDto;

public interface ScoringService {
    CreditDto calculateCredit(ScoringDataDto scoringDataDto);
}
