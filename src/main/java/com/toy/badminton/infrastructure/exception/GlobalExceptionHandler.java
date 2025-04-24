package com.toy.badminton.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ServerExceptionResponse> handleApplicationException(ApplicationException ex) {
        log.warn("errorCode = {}, errorMessage = {}, errorReason = {}", ex.getCode(), ex.getMessage(), ex.getReason(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ServerExceptionResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getReason())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerExceptionResponse> handleException(Exception ex) {
        log.error("errorMessage = {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ServerExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage())
        );
    }

    public record ServerExceptionResponse(int code, String message, String reason) {
    }

}
