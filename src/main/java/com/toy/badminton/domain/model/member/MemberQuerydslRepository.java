package com.toy.badminton.domain.model.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.toy.badminton.domain.model.member.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<Member> findMembersByIds (List<Long> memberIds) {
        return queryFactory.selectFrom(member)
                .where(member.id.in(memberIds))
                .fetch();
    }
}
