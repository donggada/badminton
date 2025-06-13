package com.toy.badminton.presentation.match.response;

import com.toy.badminton.domain.match.MatchingRoomMember;
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
                matchingRoom.getMatchingRoomMembers().stream().filter(MatchingRoomMember::isInRoom).count()
        );
    }
}
