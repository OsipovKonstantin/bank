package ru.neoflex.bank.calculator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.bank.calculator.configuration.RateConfig;
import ru.neoflex.bank.calculator.exception.ScoringRejectException;
import ru.neoflex.bank.calculator.model.dto.CreditDto;
import ru.neoflex.bank.calculator.model.dto.LoanOfferDto;
import ru.neoflex.bank.calculator.model.dto.LoanStatementRequestDto;
import ru.neoflex.bank.calculator.model.dto.ScoringDataDto;
import ru.neoflex.bank.calculator.model.enums.EmploymentStatus;
import ru.neoflex.bank.calculator.model.enums.Gender;
import ru.neoflex.bank.calculator.model.enums.MaritalStatus;
import ru.neoflex.bank.calculator.model.enums.WorkPosition;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CalculatorServiceImpl implements CalculatorService {
    private final RateConfig rateConfig;

    @Override
    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        UUID statementId = UUID.randomUUID();
        return Stream.of(
                calculateLoanOffer(statementId, true, true, loanStatementRequestDto),
                calculateLoanOffer(statementId, true, false, loanStatementRequestDto),
                calculateLoanOffer(statementId, false, true, loanStatementRequestDto),
                calculateLoanOffer(statementId, false, false, loanStatementRequestDto)
        ).sorted(Comparator.comparing(LoanOfferDto::rate).reversed()).toList();
    }

    private LoanOfferDto calculateLoanOffer(UUID statementId, boolean isInsuranceEnabled, boolean isSalaryClient,
                                            LoanStatementRequestDto loanStatementRequestDto) {
        BigDecimal rate = prescoreRate(rateConfig.getRate(), isInsuranceEnabled, isSalaryClient);

        Integer term = loanStatementRequestDto.term();
        BigDecimal amount = loanStatementRequestDto.amount();
        BigDecimal totalAmount = calculateTotalAmount(amount, term, rate, isInsuranceEnabled);
        BigDecimal monthlyPayment = calculateMonthlyPayment(totalAmount, term);

        return new LoanOfferDto(statementId,
                loanStatementRequestDto.amount(),
                totalAmount,
                term,
                monthlyPayment,
                rate,
                isInsuranceEnabled,
                isSalaryClient);
    }

    @Override
    public CreditDto calculateCredit(ScoringDataDto scoringDataDto) {
        validateScoring(scoringDataDto);

        Boolean isInsuranceEnabled = scoringDataDto.isInsuranceEnabled();
        Boolean isSalaryClient = scoringDataDto.isSalaryClient();
        BigDecimal rate = prescoreRate(rateConfig.getRate(), isInsuranceEnabled, isSalaryClient);
        rate = scoreRate(rate, scoringDataDto);

        BigDecimal amount = scoringDataDto.amount();
        Integer term = scoringDataDto.term();
        BigDecimal totalAmount = calculateTotalAmount(amount, term, rate, isInsuranceEnabled);
        BigDecimal monthlyPayment = calculateMonthlyPayment(totalAmount, term);

        return new CreditDto(
                amount,
                term,
                monthlyPayment,
                rate,
                totalAmount,
                isInsuranceEnabled,
                isSalaryClient,
                null //List<PaymentScheduleElementDto>
        );
    }

    private BigDecimal calculateTotalAmount(BigDecimal amount, Integer term, BigDecimal rate, Boolean isInsuranceEnabled) {
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);
        BigDecimal totalAmount = amount.multiply(monthlyRate).multiply(monthlyRate.add(BigDecimal.ONE).pow(term))
                .divide(monthlyRate.add(BigDecimal.ONE).pow(term).subtract(BigDecimal.ONE),
                        2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(term));

        BigDecimal insuranceRate = rateConfig.getInsuranceRate()
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        BigDecimal insuranseAmount = amount.multiply(insuranceRate);
        if (isInsuranceEnabled) {
            totalAmount = totalAmount.add(insuranseAmount);
        }
        return totalAmount;
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal totalAmount, Integer term) {
        return totalAmount.divide(BigDecimal.valueOf(term), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal prescoreRate(BigDecimal rate, boolean isInsuranceEnabled, boolean isSalaryClient) {
        rate = isInsuranceEnabled ? rate.subtract(BigDecimal.valueOf(3)) : rate;
        rate = isSalaryClient ? rate.subtract(BigDecimal.ONE) : rate;
        return rate;
    }

    private BigDecimal scoreRate(BigDecimal rate, ScoringDataDto scoringDataDto) {
        EmploymentStatus employmentStatus = scoringDataDto.employment().employmentStatus();
        if (employmentStatus == EmploymentStatus.SELF_EMPLOYED) {
            rate = rate.add(BigDecimal.TWO);
        } else if (employmentStatus == EmploymentStatus.BUSINESS_OWNER) {
            rate = rate.add(BigDecimal.ONE);
        }
        WorkPosition workPosition = scoringDataDto.employment().position();
        if (workPosition == WorkPosition.MIDDLE_MANAGER) {
            rate = rate.subtract(BigDecimal.TWO);
        } else if (workPosition == WorkPosition.TOP_MANAGER) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        }
        MaritalStatus maritalStatus = scoringDataDto.maritalStatus();
        if (maritalStatus == MaritalStatus.MARRIED) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        } else if (maritalStatus == MaritalStatus.DIVORCED) {
            rate = rate.add(BigDecimal.ONE);
        }
        Gender gender = scoringDataDto.gender();
        int age = calculateAge(scoringDataDto.birthdate());
        if ((gender == Gender.FEMALE && age >= 32 && age <= 60)
                || (gender == Gender.MALE && age >= 30 && age < 55)) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        } else if (gender == Gender.NON_BINARY) {
            rate = rate.add(BigDecimal.valueOf(7));
        }
        return rate;
    }

    private void validateScoring(ScoringDataDto scoringDataDto) {
        if (scoringDataDto.employment().employmentStatus() == EmploymentStatus.UNEMPLOYED) {
            throw new ScoringRejectException("Заявка отклонена: статус занятости — безработный");
        }
        if (scoringDataDto.amount().compareTo(scoringDataDto.employment().salary().multiply(BigDecimal.valueOf(24))) > 0) {
            throw new ScoringRejectException("Заявка отклонена: сумма займа больше, чем 24 зарплаты");
        }
        int age = calculateAge(scoringDataDto.birthdate());
        if (age < 20) {
            throw new ScoringRejectException("Заявка отклонена: возраст менее 20 лет");
        }
        if (age > 65) {
            throw new ScoringRejectException("Заявка отклонена: возраст более 65 лет");
        }
        if (scoringDataDto.employment().workExperienceTotal() < 18) {
            throw new ScoringRejectException("Заявка отклонена: общий стаж работы менее 18 месяцев");
        }
        if (scoringDataDto.employment().workExperienceCurrent() < 3) {
            throw new ScoringRejectException("Заявка отклонена: текущий стаж работы менее 3 месяцев");
        }
    }

    private int calculateAge(LocalDate birthdate) {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }
}