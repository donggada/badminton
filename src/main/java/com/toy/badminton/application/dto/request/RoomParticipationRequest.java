package com.toy.badminton.application.dto.request;

import java.util.Set;

public record RoomParticipationRequest(
        String roomName,
        Set<Integer> managerIdList
) {
}
