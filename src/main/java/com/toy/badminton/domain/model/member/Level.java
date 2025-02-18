package com.toy.badminton.domain.model.member;

import lombok.Getter;

@Getter
public enum Level {
    MASTER("준자강"),
    GROUP_A("A조"),
    GROUP_B("B조"),
    GROUP_C("C조"),
    GROUP_D("D조"),
    BEGINNER("초심");

    private final String description;


    Level(String description) {
        this.description = description;
    }
}
