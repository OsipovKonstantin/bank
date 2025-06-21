package ru.neoflex.bank.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RegularExpressionConstants {
    public static final String LATIN_TWO_TO_THIRTY_SYMBOLS = "^[a-zA-Z]{2,30}$";
    public static final String EMAIL_PATTERN = "^[a-z0-9A-Z_!#$%&'*+/=?`{|}~^.-]+@[a-z0-9A-Z.-]+$";
    public static final String FOUR_DIGITS = "^\\d{4}$";
    public static final String SIX_DIGITS = "^\\d{6}$";
    public static final String TWENTY_DIGITS ="^\\d{20}$";
    public static final String TEN_DIGITS ="^\\d{10}$";
}
