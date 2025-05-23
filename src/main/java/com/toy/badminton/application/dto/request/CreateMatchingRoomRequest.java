package com.toy.badminton.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateMatchingRoomRequest(
        @NotBlank(message = "이름은 필수 항목입니다.")
        String roomName
) {
}
