package com.toy.badminton.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_LOGIN_ID(HttpStatus.CONFLICT, "로그인 실패 입니다.", "login_id: %s"),
    INVALID_MEMBER(HttpStatus.CONFLICT, "존재하지 않는 유저입니다.", "member_id: %s"),
    MEMBER_NOT_FOUND(CONFLICT, "없는 회원 입니다.", "login_id: %s"),
    DUPLICATE_LOGIN_ID(CONFLICT, "이미 존재한 아이디 입니다.", "login_id: %s"),
    DUPLICATE_ENTER(CONFLICT, "이미 입장된 아이디 입니다.", "member_id: %d"),
    INVALID_PASSWORD(CONFLICT, "비번 확인해주세요.","비번이 일치하지 않음"),
    UNAUTHORIZED_ACCESS(UNAUTHORIZED, "회원 인증 실패입니다.","인증되지 않은 접근 입니다."),
    INVALID_MATCHING_ROOM(HttpStatus.CONFLICT, "존재하지 않는 매칭방 입니다.", "matching_id: %d"),
    INVALID_MATCHING_ROOM_INFO(HttpStatus.CONFLICT, "존재하지 않는 매칭정보 입니다.", "matching_id: %d, member_id: %d"),
    INVALID_MATCHING_SERVICE(HttpStatus.CONFLICT, "존재하지 매칭서비스 타입 입니다.", "matching_type: %s"),
    MATCHING_ROOM_EDIT_FORBIDDEN(HttpStatus.CONFLICT, "매칭 수정권한이 없습니다.", "member_id: %d"),
    REQUESTER_NOT_FOUND(HttpStatus.CONFLICT, "요청자가 매칭방에 존재하지 않습니다..", "member_id: %d"),
    TARGET_NOT_FOUND(HttpStatus.CONFLICT, "교체 대상 멤버를 찾을 수 없습니다.", "member_id: %d"),

    NOT_ENOUGH_MATCHING_MEMBERS(HttpStatus.CONFLICT, "매칭 가능한 멤버 수가 부족합니다.", "min_size: %d, current_size: %d");


    private final HttpStatus httpStatus;
    private final String message;
    private final String reason;

    public ApplicationException build(Object ...args) {
        return new ApplicationException(httpStatus, message, reason.formatted(args));
    }
}
