package ru.neoflex.bank.calculator.exception;

public class ScoringRejectException extends RuntimeException {
    public ScoringRejectException(String message) {
        super(message);
    }
}
