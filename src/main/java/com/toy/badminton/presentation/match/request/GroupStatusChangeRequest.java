package com.toy.badminton.presentation.match.request;

import com.toy.badminton.domain.match.MatchingStatus;
import jakarta.validation.constraints.NotNull;

public record GroupStatusChangeRequest(
        @NotNull(message = "그룹 ID는 필수 입니다.")
        Long groupId,
        @NotNull(message = "상태값은 필수 입니다.")
        MatchingStatus status
) {
}
