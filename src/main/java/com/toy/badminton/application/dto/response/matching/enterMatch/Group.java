package com.toy.badminton.application.dto.response.matching.enterMatch;

import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;

import java.util.List;

record Group (
        Long id,
        List<EnterMember> memberList
) {

    public static Group of (MatchGroup matchGroup) {
        return new Group(matchGroup.getId(), matchGroup.getMembers().stream().map(EnterMember::of).toList());
    }
}

