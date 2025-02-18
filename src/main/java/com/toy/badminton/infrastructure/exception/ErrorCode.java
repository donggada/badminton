package com.toy.badminton.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_MEMBER(HttpStatus.CONFLICT, "존재하지 않는 유저입니다.", "login_id: %d");

    private final HttpStatus httpStatus;
    private final String message;
    private final String reason;

    public ApplicationException build(Object ...args) {
        return new ApplicationException(httpStatus, message, reason.formatted(args));
    }
}
