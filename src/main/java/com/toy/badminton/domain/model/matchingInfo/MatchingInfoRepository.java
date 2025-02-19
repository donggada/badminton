package com.toy.badminton.domain.model.matchingInfo;

import com.toy.badminton.domain.model.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchingInfoRepository extends JpaRepository<MatchingInfo, Long> {

    Optional<MatchingInfo> findByMatchingRoomAndMember(MatchingRoom matchingRoom, Member member);
}
