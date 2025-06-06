package com.toy.badminton.application.dto.response.matching;

import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;

public record RoomParticipationResponse(
        String matchingRoomName,
        String username,
        String message
)
 {
     public static RoomParticipationResponse of (MatchingInfo matchingInfo) {
         return new RoomParticipationResponse(
                 matchingInfo.getMatchingRoomName(),
                 matchingInfo.getMemberName(),
                 matchingInfo.getMessage()
         );
     }
}
