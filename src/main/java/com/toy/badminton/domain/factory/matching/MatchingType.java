package com.toy.badminton.domain.factory.matching;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchingType {
    RANDOM("랜덤"),
    BALANCED("밸런스");

    private final String description;
}
