package com.toy.badminton.presentation.member.request;

import com.toy.badminton.domain.member.Level;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberProfileRequest(
        @NotBlank(message = "이름은 필수 항목입니다.")
        String name,

        String profileImage,

        @NotNull(message = "레벨은 필수 항목입니다.")
        Level level
) {
} 