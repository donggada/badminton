package com.toy.badminton.application.dto.response;

import com.toy.badminton.domain.model.member.Level;
import com.toy.badminton.domain.model.member.Member;

public record LoginResponse(
        String loginId,
        String username,
        String phoneNumber,
        Level level,
        String message
) {
    public static LoginResponse of (Member member) {
        return new LoginResponse(
                member.getLoginId(),
                member.getUsername(),
                member.getPhoneNumber(),
                member.getLevel(),
                "로그인 성공"
        );
    }
}
