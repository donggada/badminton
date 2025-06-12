package com.toy.badminton.presentation.member.request;

import jakarta.validation.constraints.NotBlank;

public record DeleteMemberRequest(
        @NotBlank(message = "비밀번호는 필수 항목입니다.")
        String password
) {
} 