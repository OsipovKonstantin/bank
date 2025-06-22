package ru.neoflex.bank.calculator.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.bank.common.model.dto.EmploymentDto;
import ru.neoflex.bank.common.model.dto.ScoringDataDto;
import ru.neoflex.bank.common.model.enums.EmploymentStatus;
import ru.neoflex.bank.common.model.enums.Gender;
import ru.neoflex.bank.common.model.enums.MaritalStatus;
import ru.neoflex.bank.common.model.enums.WorkPosition;
import ru.neoflex.bank.common.util.TestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.neoflex.bank.common.util.MoneyUtils.*;

@ExtendWith(MockitoExtension.class)
class RateAdjustmentServiceTest {
    @InjectMocks
    private RateAdjustmentService rateAdjustmentService;

    @ParameterizedTest
    @MethodSource("prescoreRateTestCases")
    void prescoreRate(BigDecimal rate, Boolean isInsuranceEnabled, Boolean isSalaryClient, BigDecimal returnedRate) {
        assertEquals(returnedRate, rateAdjustmentService.prescoreRate(rate, isInsuranceEnabled, isSalaryClient));
    }

    static Stream<Arguments> prescoreRateTestCases() {
        BigDecimal baseRate = BigDecimal.valueOf(20);
        return Stream.of(
                Arguments.of(baseRate, false, false, baseRate),
                Arguments.of(baseRate, true, false, baseRate.subtract(HAS_INSURANCE_DISCOUNT)),
                Arguments.of(baseRate, false, true, baseRate.subtract(SALARY_CLIENT_DISCOUNT)),
                Arguments.of(baseRate, true, true, baseRate.subtract(HAS_INSURANCE_DISCOUNT)
                        .subtract(SALARY_CLIENT_DISCOUNT))
        );
    }

    @ParameterizedTest
    @MethodSource("scoreRateTestCases")
    void scoreRate(ScoringDataDto scoringDataDto, BigDecimal prescoredRate, BigDecimal scoredRate) {
        assertEquals(rateAdjustmentService.scoreRate(prescoredRate, scoringDataDto), scoredRate);
    }

    static Stream<Arguments> scoreRateTestCases() {
        ScoringDataDto validScoringDataDto = TestUtils.getValidScoringDataDto();
        EmploymentDto validEmploymentDto = TestUtils.getValidEmployment();
        BigDecimal prescoredRate = BigDecimal.valueOf(20);
        return Stream.of(
                Arguments.of(validScoringDataDto, prescoredRate, prescoredRate.add(SELF_EMPLOYED_PENALTY)
                        .subtract(MARRIED_DISCOUNT).subtract(WORKING_AGE_DISCOUNT)),
                Arguments.of(validScoringDataDto.withEmployment(validEmploymentDto
                                .withEmploymentStatus(EmploymentStatus.BUSINESS_OWNER)), prescoredRate,
                        prescoredRate.add(BUSINESS_OWNER_PENALTY).subtract(MARRIED_DISCOUNT)
                                .subtract(WORKING_AGE_DISCOUNT)),
                Arguments.of(validScoringDataDto.withEmployment(validEmploymentDto
                                .withPosition(WorkPosition.MIDDLE_MANAGER)), prescoredRate,
                        prescoredRate.add(SELF_EMPLOYED_PENALTY).subtract(MIDDLE_MANAGER_DISCOUNT)
                                .subtract(MARRIED_DISCOUNT).subtract(WORKING_AGE_DISCOUNT)),
                Arguments.of(validScoringDataDto.withEmployment(validEmploymentDto
                        .withPosition(WorkPosition.TOP_MANAGER)), prescoredRate, prescoredRate
                        .add(SELF_EMPLOYED_PENALTY).subtract(TOP_MANAGER_DISCOUNT).subtract(MARRIED_DISCOUNT)
                                .subtract(WORKING_AGE_DISCOUNT)),
                Arguments.of(validScoringDataDto.withMaritalStatus(MaritalStatus.DIVORCED), prescoredRate,
                prescoredRate.add(SELF_EMPLOYED_PENALTY).add(DIVORCED_PENALTY).subtract(WORKING_AGE_DISCOUNT)),
                Arguments.of(validScoringDataDto.withBirthdate(LocalDate.now().minusYears(29)),
                        prescoredRate, prescoredRate.add(SELF_EMPLOYED_PENALTY).subtract(MARRIED_DISCOUNT)),
                Arguments.of(validScoringDataDto.withBirthdate(LocalDate.now().minusYears(56)),
                        prescoredRate, prescoredRate.add(SELF_EMPLOYED_PENALTY).subtract(MARRIED_DISCOUNT)),
                Arguments.of(validScoringDataDto.withGender(Gender.FEMALE).withBirthdate(LocalDate.now()
                        .minusYears(31)), prescoredRate, prescoredRate.add(SELF_EMPLOYED_PENALTY)
                        .subtract(MARRIED_DISCOUNT)),
                Arguments.of(validScoringDataDto.withGender(Gender.FEMALE).withBirthdate(LocalDate.now()
                        .minusYears(32)), prescoredRate, prescoredRate.add(SELF_EMPLOYED_PENALTY)
                        .subtract(MARRIED_DISCOUNT).subtract(WORKING_AGE_DISCOUNT)),
                Arguments.of(validScoringDataDto.withGender(Gender.FEMALE).withBirthdate(LocalDate.now()
                        .minusYears(60)), prescoredRate, prescoredRate.add(SELF_EMPLOYED_PENALTY)
                        .subtract(MARRIED_DISCOUNT).subtract(WORKING_AGE_DISCOUNT)),
                Arguments.of(validScoringDataDto.withGender(Gender.FEMALE).withBirthdate(LocalDate.now()
                        .minusYears(61)), prescoredRate, prescoredRate.add(SELF_EMPLOYED_PENALTY)
                        .subtract(MARRIED_DISCOUNT)),
                Arguments.of(validScoringDataDto.withGender(Gender.NON_BINARY), prescoredRate,
                        prescoredRate.add(SELF_EMPLOYED_PENALTY).subtract(MARRIED_DISCOUNT).add(NON_BINARY_GENDER_PENALTY))
                );
    }
}