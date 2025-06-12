package com.toy.badminton.presentation.match.response;

import com.toy.badminton.domain.match.MatchGroup;

import java.util.List;

record Group (
        Long id,
        List<EnterMember> memberList
) {

    public static Group of (MatchGroup matchGroup) {
        return new Group(matchGroup.getId(), matchGroup.getMembers().stream().map(EnterMember::of).toList());
    }
}

