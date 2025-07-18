package ru.neoflex.deal.util;

import lombok.experimental.UtilityClass;
import ru.neoflex.deal.model.dto.*;
import ru.neoflex.deal.model.enums.EmploymentPosition;
import ru.neoflex.deal.model.enums.EmploymentStatus;
import ru.neoflex.deal.model.enums.Gender;
import ru.neoflex.deal.model.enums.MaritalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class TestUtils {
    public static CreditDto getValidCreditDto() {
        return CreditDto.builder()
                .amount(new BigDecimal("1000000.50"))
                .term(40)
                .monthlyPayment(new BigDecimal("17560.31"))
                .rate(new BigDecimal("17.4"))
                .psk(new BigDecimal("331540.69"))
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .paymentSchedule(List.of(
                        getValidPaymentScheduleElementDto(),
                        getValidPaymentScheduleElementDto()
                ))
                .build();
    }

    public static PaymentScheduleElementDto getValidPaymentScheduleElementDto() {
        return PaymentScheduleElementDto.builder()
                .number(1)
                .date(LocalDate.of(2025, 4, 5))
                .totalPayment(new BigDecimal("17560.31"))
                .interestPayment(new BigDecimal("12430.10"))
                .debtPayment(new BigDecimal("5130.21"))
                .remainingDebt(new BigDecimal("994869.79"))
                .build();
    }

    public static EmploymentDto getValidEmployment() {
        return EmploymentDto.builder()
                .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
                .EmployerINN("7783572942")
                .salary(new BigDecimal("51400.5"))
                .position(EmploymentPosition.WORKER)
                .workExperienceTotal(20)
                .workExperienceCurrent(8)
                .build();
    }

    LoanOfferDto validLoanOffer = LoanOfferDto.builder()
            .statementId(UUID.fromString("09b84931-b217-426a-a618-4b51af2d3bb1"))
            .requestedAmount(new BigDecimal("1000000.50"))
            .totalAmount(new BigDecimal("331540.69"))
            .term(40)
            .monthlyPayment(new BigDecimal("17560.31"))
            .rate(new BigDecimal("17.4"))
            .isInsuranceEnabled(true)
            .isSalaryClient(false)
            .build();

    public static ScoringDataDto getValidScoringDataDto() {
        return ScoringDataDto.builder()
                .amount(new BigDecimal("1000000.50"))
                .term(40)
                .firstName("Ivan")
                .lastName("Petrov")
                .middleName("Sidorovich")
                .gender(Gender.MALE)
                .birthdate(LocalDate.of(1995, 6, 1))
                .passportSeries("4013")
                .passportNumber("049647")
                .passportIssueDate(LocalDate.of(2014, 7, 2))
                .passportIssueBranch("УФМС России по г. Москве")
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(2)
                .employment(getValidEmployment())
                .accountNumber("40817810700030001234")
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .build();
    }
}
