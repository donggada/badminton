package com.toy.badminton.application.dto.request;

import com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeMatchingStatusRequest(
        @NotNull(message = "상태값은 필수입니다")
        MatchingStatus status
) {
}
