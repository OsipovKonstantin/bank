package ru.neoflex.bank.calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.bank.calculator.model.dto.ScoringDataDto;
import ru.neoflex.bank.calculator.model.enums.EmploymentStatus;
import ru.neoflex.bank.calculator.model.enums.Gender;
import ru.neoflex.bank.calculator.model.enums.MaritalStatus;
import ru.neoflex.bank.calculator.model.enums.WorkPosition;
import ru.neoflex.bank.calculator.util.DateTimeUtils;
import ru.neoflex.bank.logging.Logging;

import java.math.BigDecimal;

import static ru.neoflex.bank.calculator.util.MoneyUtils.*;

@Logging
@Service
@RequiredArgsConstructor
public class RateAdjustmentService {
    public BigDecimal prescoreRate(BigDecimal rate, boolean isInsuranceEnabled, boolean isSalaryClient) {
        rate = isInsuranceEnabled ? rate.subtract(HAS_INSURANCE_DISCOUNT) : rate;
        rate = isSalaryClient ? rate.subtract(SALARY_CLIENT_DISCOUNT) : rate;
        return rate;
    }

    public BigDecimal scoreRate(BigDecimal rate, ScoringDataDto scoringDataDto) {
        rate = adjustForEmploymentStatus(rate, scoringDataDto.employment().employmentStatus());
        rate = adjustForPosition(rate, scoringDataDto.employment().position());
        rate = adjustForMaritalStatus(rate, scoringDataDto.maritalStatus());
        rate = adjustForGenderAndAge(rate, scoringDataDto.gender(), DateTimeUtils.calculateAge(scoringDataDto.birthdate()));
        return rate;
    }

    private BigDecimal adjustForGenderAndAge(BigDecimal rate, Gender gender, int age) {
        if ((gender == Gender.FEMALE && age >= 32 && age <= 60)
                || (gender == Gender.MALE && age >= 30 && age < 55)) {
            rate = rate.subtract(WORKING_AGE_DISCOUNT);
        } else if (gender == Gender.NON_BINARY) {
            rate = rate.add(NON_BINARY_GENDER_PENALTY);
        }
        return rate;
    }

    private BigDecimal adjustForMaritalStatus(BigDecimal rate, MaritalStatus maritalStatus) {
        if (maritalStatus == MaritalStatus.MARRIED) {
            rate = rate.subtract(MARRIED_DISCOUNT);
        } else if (maritalStatus == MaritalStatus.DIVORCED) {
            rate = rate.add(DIVORCED_PENALTY);
        }
        return rate;
    }

    private BigDecimal adjustForPosition(BigDecimal rate, WorkPosition position) {
        if (position == WorkPosition.MIDDLE_MANAGER) {
            rate = rate.subtract(MIDDLE_MANAGER_DISCOUNT);
        } else if (position == WorkPosition.TOP_MANAGER) {
            rate = rate.subtract(TOP_MANAGER_DISCOUNT);
        }
        return rate;
    }

    private BigDecimal adjustForEmploymentStatus(BigDecimal rate, EmploymentStatus employmentStatus) {
        if (employmentStatus == EmploymentStatus.SELF_EMPLOYED) {
            rate = rate.add(SELF_EMPLOYED_PENALTY);
        } else if (employmentStatus == EmploymentStatus.BUSINESS_OWNER) {
            rate = rate.add(BUSINESS_OWNER_PENALTY);
        }
        return rate;
    }
}