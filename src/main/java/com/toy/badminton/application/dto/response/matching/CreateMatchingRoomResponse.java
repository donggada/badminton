package com.toy.badminton.application.dto.response.matching;

import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;

public record CreateMatchingRoomResponse(
        Long id,
        String roomName,
        String message
) {

    public static CreateMatchingRoomResponse of (MatchingRoom matchingRoom) {
        return new CreateMatchingRoomResponse(
                matchingRoom.getId(),
                matchingRoom.getName(),
                "매칭방 생성 성공"
        );
    }
}
