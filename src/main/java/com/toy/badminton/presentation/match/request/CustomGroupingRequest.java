package com.toy.badminton.presentation.match.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

import static com.toy.badminton.domain.match.MatchingService.DOUBLES;

public record CustomGroupingRequest(
        @NotNull(message = "멤버 ID 목록은 필수입니다.")
        @Size(min = DOUBLES, max = DOUBLES, message = "멤버 ID는 정확히 " + DOUBLES + "개여야 합니다.")
        List<Long> memberIds
) {
}
