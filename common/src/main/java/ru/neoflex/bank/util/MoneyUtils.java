package ru.neoflex.bank.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@UtilityClass
public class MoneyUtils {
    public static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    public static final BigDecimal HAS_INSURANCE_DISCOUNT = BigDecimal.valueOf(3);
    public static final BigDecimal TOP_MANAGER_DISCOUNT = BigDecimal.valueOf(3);
    public static final BigDecimal MARRIED_DISCOUNT = BigDecimal.valueOf(3);
    public static final BigDecimal MIDDLE_MANAGER_DISCOUNT = BigDecimal.TWO;
    public static final BigDecimal DIVORCED_PENALTY = BigDecimal.ONE;
    public static final BigDecimal WORKING_AGE_DISCOUNT = BigDecimal.valueOf(3);
    public static final BigDecimal NON_BINARY_GENDER_PENALTY = BigDecimal.valueOf(7);
    public static final BigDecimal SELF_EMPLOYED_PENALTY = BigDecimal.TWO;
    public static final BigDecimal BUSINESS_OWNER_PENALTY = BigDecimal.ONE;
    public static final BigDecimal SALARY_CLIENT_DISCOUNT = BigDecimal.ONE;
    public static final MathContext SIXTEEN_DIGITS = MathContext.DECIMAL64;

    public static BigDecimal calculateMonthlyRate(BigDecimal rate) {
        return rate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);
    }

    public static BigDecimal moneyScale(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal rateScale(BigDecimal value) {
        return value.setScale(3, RoundingMode.HALF_UP);
    }
}