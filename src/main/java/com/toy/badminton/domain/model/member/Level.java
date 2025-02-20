package com.toy.badminton.domain.model.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Level {
    MASTER("준자강", 1),
    GROUP_A("A조",2),
    GROUP_B("B조",3),
    GROUP_C("C조",4),
    GROUP_D("D조",5),
    BEGINNER("초심",6);

    private final String description;
    private final int value;
}
