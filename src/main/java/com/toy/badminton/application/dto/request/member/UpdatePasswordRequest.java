package com.toy.badminton.application.dto.request.member;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequest(
        @NotBlank(message = "새 비밀번호는 필수 항목입니다.")
        String newPassword
) {
} 