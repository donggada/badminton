package com.toy.badminton.presentation.member.request;

import com.toy.badminton.domain.member.Level;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberSignupRequest(
        @NotBlank(message = "사용자 이름은 필수 항목입니다.")
        String username,

        @NotBlank(message = "비밀번호 은 필수 항목입니다.")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,

        @NotBlank(message = "아이디는 필수 항목입니다.")
        String loginId,

        @NotNull(message = "전화번호 필수 항목입니다.")
        @Pattern(regexp = "^(\\+82-?)?(010|011|016|017|018|019)-?\\d{3,4}-?\\d{4}$",
                message = "전화번호 형식이 올바르지 않습니다.")
        String phoneNumber,

        @NotNull(message = "급수 설정은 필수 항목입니다.")
        Level level
) {
}
