package com.toy.badminton.presentation.match.request;

import java.util.Set;

public record RoomParticipationRequest(
        String roomName,
        Set<Long> managerIdList
) {
}
