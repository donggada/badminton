package com.toy.badminton.presentation.match.response;

import com.toy.badminton.domain.match.MatchingInfo;
import com.toy.badminton.domain.match.MatchingRoom;

public record MatchingRoomResponse(
        Long id,
        String name,
        Long memberCount
) {

    public static MatchingRoomResponse of (MatchingRoom matchingRoom) {
        return new MatchingRoomResponse(
                matchingRoom.getId(), 
                matchingRoom.getName(),
                matchingRoom.getMatchingInfos().stream().filter(MatchingInfo::isInRoom).count()
        );
    }
}
