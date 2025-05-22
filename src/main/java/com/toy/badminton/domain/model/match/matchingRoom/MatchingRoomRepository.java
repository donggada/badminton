package com.toy.badminton.domain.model.match.matchingRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MatchingRoomRepository extends JpaRepository<MatchingRoom, Long> {
}
