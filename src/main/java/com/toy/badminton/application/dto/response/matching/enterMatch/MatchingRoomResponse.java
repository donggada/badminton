package com.toy.badminton.application.dto.response.matching.enterMatch;

import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;

import java.util.List;

public record MatchingRoomResponse(
        Long id,
        String name,
        List<EnterMember> enterMebmerList,
        List<Group> groupList
) {
    public static MatchingRoomResponse of (MatchingRoom matchingRoom) {
        return new MatchingRoomResponse(
                matchingRoom.getId(),
                matchingRoom.getName(),
                matchingRoom.getMatchingInfos().stream().map(EnterMember::of).toList(),
                matchingRoom.getMatchGroups().stream().map(Group::of).toList()
        );
    }
}
