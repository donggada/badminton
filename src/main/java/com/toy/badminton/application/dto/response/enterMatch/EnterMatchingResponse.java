package com.toy.badminton.application.dto.response.enterMatch;

import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;

import java.util.List;

public record EnterMatchingResponse(
        Long id,
        String name,
        List<EnterMember> enterMebmerList,
        List<Group> groupList
) {
    public static EnterMatchingResponse of (MatchingRoom matchingRoom) {
        return new EnterMatchingResponse(
                matchingRoom.getId(),
                matchingRoom.getName(),
                matchingRoom.getMatchingInfos().stream().map(EnterMember::of).toList(),
                matchingRoom.getMatchGroups().stream().map(Group::of).toList()
        );
    }
}
