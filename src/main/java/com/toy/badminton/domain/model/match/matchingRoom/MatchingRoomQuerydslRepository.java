package com.toy.badminton.domain.model.match.matchingRoom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.toy.badminton.domain.model.match.matchingInfo.QMatchingInfo.matchingInfo;
import static com.toy.badminton.domain.model.match.matchingRoom.QMatchingRoom.matchingRoom;
import static com.toy.badminton.domain.model.member.QMember.member;

@Repository
@RequiredArgsConstructor
public class MatchingRoomQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<MatchingRoom> findWithAllById(Long matchingRoomId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(matchingRoom)
                .leftJoin(matchingRoom.matchingInfos, matchingInfo).fetchJoin()
                .leftJoin(matchingInfo.member, member).fetchJoin()
                .where(matchingRoom.id.eq(matchingRoomId))
                .fetchOne()
        );
    }

    public List<MatchingRoom> findMatchingRoomsWithActiveMembers(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return queryFactory.selectFrom(matchingRoom)
                .leftJoin(matchingRoom.matchingInfos, matchingInfo).fetchJoin()
//                .where(matchingRoom.createdDate.between(startOfDay, endOfDay))
                .fetch();
    }
}
