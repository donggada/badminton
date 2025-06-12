package com.toy.badminton.presentation.match.request;

import com.toy.badminton.domain.match.MatchingStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeMatchingStatusRequest(
        @NotNull(message = "상태값은 필수입니다")
        MatchingStatus status
) {
}
