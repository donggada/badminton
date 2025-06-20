package com.toy.badminton.presentation.member.response;

public record LoginResponse(String loginId, String token, String message) {

    public static LoginResponse of (String loginId, String token) {
        return new LoginResponse(loginId, token, "로그인 성공");
    }
}
