package com.toy.badminton.application.dto.request.manager;

import com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus;
import jakarta.validation.constraints.NotNull;

public record GroupStatusChangeRequest(
        @NotNull(message = "그룹 ID는 필수 입니다.")
        Long groupId,
        @NotNull(message = "상태값은 필수 입니다.")
        MatchingStatus status
) {
}
