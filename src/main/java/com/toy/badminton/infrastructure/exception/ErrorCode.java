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
    INVALID_ENTRY_CODE(HttpStatus.BAD_REQUEST, "유효하지 않은 입장 코드입니다", "entry_code: %s"),
    INVALID_MATCHING_GROUP(HttpStatus.CONFLICT, "존재하지 매칭 입니다.", "matching_group: %d"),
    INVALID_MATCHING_ROOM_INFO(HttpStatus.CONFLICT, "존재하지 않는 매칭정보 입니다.", "matching_id: %d, member_id: %d"),
    INVALID_MATCHING_SERVICE(HttpStatus.CONFLICT, "존재하지 매칭서비스 타입 입니다.", "matching_type: %s"),
    MATCHING_ROOM_EDIT_FORBIDDEN(HttpStatus.CONFLICT, "매칭 수정권한이 없습니다.", "member_id: %d"),
    REQUESTER_NOT_FOUND(HttpStatus.CONFLICT, "요청자가 매칭방에 존재하지 않습니다.", "member_id: %d"),
    TARGET_NOT_FOUND(HttpStatus.CONFLICT, "교체 대상 멤버를 찾을 수 없습니다.", "member_id: %d"),
    MEMBER_ALREADY_IN_GROUP(HttpStatus.CONFLICT, "교체 대상 이미 그룹에 있습니다.", "matching_group: %d, member_id: %d"),
    NOT_ENOUGH_MATCHING_MEMBERS(HttpStatus.CONFLICT, "매칭 가능한 멤버 수가 부족합니다.", "min_size: %d, current_size: %d"),
    WITHDRAWN_MEMBER(HttpStatus.CONFLICT, "탈퇴한 회원입니다.", "member_id: %d"),
    DAILY_ROOM_CREATION_LIMIT(HttpStatus.CONFLICT, "활성화된 매칭룸은 하루에 한 개만 생성 가능합니다", ""),
    INACTIVE_MATCHING_ROOM(HttpStatus.CONFLICT, "비활성화된 매칭룸입니다.", "matching_room_id: %d"),
    MANAGER_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "매니저 삭제 권한이 없습니다", "member_id: %d"),
    ROOM_MEMBER_NOT_IN_ROOM(HttpStatus.CONFLICT, "회원이 매칭방에 존재하지 않습니다.", "matchingRoomMember_ids: %s"),
    MEMBER_NOT_IN_ROOM(HttpStatus.CONFLICT, "회원이 매칭방에 존재하지 않습니다.", "member_id: %d");

    private final HttpStatus httpStatus;
    private final String message;
    private final String reason;

    public ApplicationException build(Object ...args) {
        return new ApplicationException(httpStatus, message, reason.formatted(args));
    }
}
