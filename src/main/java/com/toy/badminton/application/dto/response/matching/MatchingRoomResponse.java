package com.toy.badminton.application.dto.response.matching;

import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;

public record MatchingRoomResponse(
        Long id,
        String name,
        int memberCount
) {

    public static MatchingRoomResponse of (MatchingRoom matchingRoom) {
        return new MatchingRoomResponse(
                matchingRoom.getId(), 
                matchingRoom.getName(),
                matchingRoom.getMatchingInfos().size()
        );
    }
}
