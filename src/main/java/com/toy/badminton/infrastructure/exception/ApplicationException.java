package com.toy.badminton.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;



@Getter
public class ApplicationException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    private final String reason;

    public ApplicationException(HttpStatus httpStatus ,String message, String reason) {
        this.httpStatus = httpStatus;
        this.code = httpStatus.toString();
        this.message = message;
        this.reason = reason;
    }
}

