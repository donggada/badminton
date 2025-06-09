package com.toy.badminton.application.dto.response.matching;

import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;

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
