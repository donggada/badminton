package com.toy.badminton.domain.match;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchingStatus {
    WAITING("대기 중"),
    MATCHED("매칭 완료"),
    IN_GAME("게임 중"),
    COMPLETED("게임 완료"),
    MATCHING_INACTIVE("매칭 비활성화"),
    LEFT_ROOM("방에서 나감");

    private final String description;

    public boolean isCompleted() {
        return this == COMPLETED;
    }
}
