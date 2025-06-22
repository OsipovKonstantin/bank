package ru.neoflex.bank.calculator.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import org.springframework.http.HttpStatus;
import ru.neoflex.bank.common.util.DateTimeUtils;

import java.util.Date;

@Hidden
@Data
public class ErrorResponse {
    private HttpStatus status;
    private String message;

    @JsonFormat(pattern = DateTimeUtils.DATE_TIME_PATTERN)
    private Date timestamp;

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
