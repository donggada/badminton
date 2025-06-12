package com.toy.badminton.presentation.member.response;

import com.toy.badminton.domain.member.Member;

public record MemberSignupResponse(
        Long id,
        String username,
        String level,
        String message
) {

    public static MemberSignupResponse of (Member member) {
        return new MemberSignupResponse(
                member.getId(),
                member.getUsername(),
                member.getLevelDescription(),
                "회원가입 성공"
        );
    }

}
