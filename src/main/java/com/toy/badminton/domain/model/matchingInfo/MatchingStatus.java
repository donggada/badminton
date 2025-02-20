package com.toy.badminton.domain.model.matchingInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchingStatus {
    WAITING("대기 중"),
    IN_GAME("게임 중"),
    MATCHING_INACTIVE("매칭 비활성화"),
    LEFT_ROOM("방에서 나감");

    private final String description;
}
