package com.toy.badminton.application.dto.response;

import com.toy.badminton.domain.model.matchingRoom.MatchingRoom;

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
