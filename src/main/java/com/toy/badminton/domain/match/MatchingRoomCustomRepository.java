package com.toy.badminton.domain.match;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MatchingRoomCustomRepository {
    Optional<MatchingRoom> findRoomWithDetailsById(Long matchingRoomId);

    List<MatchingRoom> findMatchingRoomsWithActiveMembers(LocalDateTime startOfDay, LocalDateTime endOfDay);

    Optional<MatchingRoom> findTodayCreatedRoomByMemberId(Long memberId);

    Optional<MatchingRoom> findRoomWithDetailsByEntryCode(String entryCode);
}
