package com.toy.badminton.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_LOGIN_ID(HttpStatus.CONFLICT, "로그인 실패 입니다.", "login_id: %d"),
    INVALID_MEMBER(HttpStatus.CONFLICT, "존재하지 않는 유저입니다.", "member_id: %d"),
    INVALID_MATCHING_ROOM(HttpStatus.CONFLICT, "존재하지 않는 매칭방 입니다.", "matching_id: %d"),
    INVALID_MATCHING_ROOM_INFO(HttpStatus.CONFLICT, "존재하지 않는 매칭정보 입니다.", "matching_id: %d, member_id: %d"),
    INVALID_MATCHING_SERVICE(HttpStatus.CONFLICT, "존재하지 매칭서비스 타입 입니다.", "matching_type: %d"),
    NOT_ENOUGH_MATCHING_MEMBERS(HttpStatus.CONFLICT, "매칭 가능한 멤버 수가 부족합니다.", "min_size: %d, current_size: %d");


    private final HttpStatus httpStatus;
    private final String message;
    private final String reason;

    public ApplicationException build(Object ...args) {
        return new ApplicationException(httpStatus, message, reason.formatted(args));
    }
}
