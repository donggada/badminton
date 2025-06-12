package com.toy.badminton.presentation.member.request;

public record LoginRequest(
        String loginId,
        String password) {
}
