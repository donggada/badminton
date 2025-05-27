package com.toy.badminton.application.dto.request.member;

import com.toy.badminton.domain.model.member.Level;
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