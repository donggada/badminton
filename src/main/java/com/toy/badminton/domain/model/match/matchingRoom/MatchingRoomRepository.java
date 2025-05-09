package com.toy.badminton.domain.model.match.matchingRoom;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchingRoomRepository extends JpaRepository<MatchingRoom, Long> {

    //todo QueryDSL 변경
    @EntityGraph(attributePaths = {"managerList"})
    Optional<MatchingRoom> findWithAllById(Long id);
}
