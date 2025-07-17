package ru.neoflex.deal.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExceptionMessage {
    private String timestamp;
    private String status;
    private String error;
    private String message;
    private String path;
}
