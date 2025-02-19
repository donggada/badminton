package com.toy.badminton.application.dto.response;

import com.toy.badminton.domain.model.matchingInfo.MatchingInfo;

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
