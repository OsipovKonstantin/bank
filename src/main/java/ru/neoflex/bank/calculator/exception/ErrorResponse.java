package ru.neoflex.bank.calculator.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;
import ru.neoflex.bank.calculator.util.DateTimeConstants;

import java.util.Date;

@Data
public class ErrorResponse {
    private HttpStatus status;
    private String message;

    @JsonFormat(pattern = DateTimeConstants.DATE_TIME_PATTERN)
    private Date timestamp;

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
