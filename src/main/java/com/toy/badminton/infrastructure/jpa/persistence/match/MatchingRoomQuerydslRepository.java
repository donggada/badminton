package com.toy.badminton.infrastructure.jpa.persistence.match;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.badminton.domain.match.MatchingRoom;
import com.toy.badminton.domain.match.MatchingRoomCustomRepository;
import com.toy.badminton.domain.match.QMatchGroup;
import com.toy.badminton.domain.match.QMatchingRoomMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


import static com.toy.badminton.domain.match.QMatchingRoom.matchingRoom;
import static com.toy.badminton.domain.match.QMatchingRoomMember.matchingRoomMember;
import static com.toy.badminton.domain.member.QMember.member;


@Repository
@RequiredArgsConstructor
public class MatchingRoomQuerydslRepository implements MatchingRoomCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<MatchingRoom> findRoomWithDetailsById(Long matchingRoomId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(matchingRoom)
                        .where(matchingRoom.id.eq(matchingRoomId))
                        .fetchOne()
        );
    }


    @Override
    public List<MatchingRoom> findMatchingRoomsWithActiveMembers(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return queryFactory.selectFrom(matchingRoom)
                .leftJoin(matchingRoom.matchingRoomMembers, matchingRoomMember).fetchJoin()
//                .where(matchingRoom.createdDate.between(startOfDay, endOfDay))
                .where(matchingRoom.isActive.isTrue())
                .fetch();
    }

    @Override
    public Optional<MatchingRoom> findTodayCreatedRoomByMemberId(Long memberId) {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        return Optional.ofNullable(
                queryFactory.selectFrom(matchingRoom)
                        .where(matchingRoom.createdDate.between(startOfDay, endOfDay))
                        .where(matchingRoom.createdId.eq(memberId))
                        .where(matchingRoom.isActive.isTrue())
                        .fetchOne()
        );
    }

    @Override
    public Optional<MatchingRoom> findRoomWithDetailsByEntryCode(String entryCode) {
        return Optional.ofNullable(
                queryFactory.selectFrom(matchingRoom)
                        .leftJoin(matchingRoom.matchingRoomMembers, matchingRoomMember).fetchJoin()
                        .leftJoin(matchingRoomMember.member, member).fetchJoin()
                        .where(matchingRoom.entryCode.eq(entryCode))
                        .fetchOne()
        );
    }
}
