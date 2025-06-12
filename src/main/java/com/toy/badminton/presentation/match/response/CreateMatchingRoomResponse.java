package com.toy.badminton.presentation.match.response;

import com.toy.badminton.domain.match.MatchingRoom;

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
