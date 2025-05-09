package com.toy.badminton.domain.model.match.matchingRoom;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.toy.badminton.domain.model.match.matchingRoom.QMatchingRoom.matchingRoom;

@Repository
@RequiredArgsConstructor
public class MatchingRoomQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<MatchingRoom> findWithAllById(Long id) {
        MatchingRoom room = queryFactory
                .selectFrom(matchingRoom)
                .where(matchingRoom.id.eq(id))
                .fetchOne();

        if (room == null) {
            return Optional.empty();
        }

        Hibernate.initialize(room.getMatchingInfos());
        Hibernate.initialize(room.getMatchGroups());
        Hibernate.initialize(room.getManagerList());

        // 3. MatchGroup의 members도 초기화
        for (MatchGroup group : room.getMatchGroups()) {
            Hibernate.initialize(group.getMembers());
        }

        // 4. MatchingInfo의 member도 초기화
        for (MatchingInfo info : room.getMatchingInfos()) {
            Hibernate.initialize(info.getMember());
        }

        return Optional.of(room);
    }
} 
