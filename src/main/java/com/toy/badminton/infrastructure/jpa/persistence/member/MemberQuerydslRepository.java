package com.toy.badminton.infrastructure.jpa.persistence.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.badminton.domain.member.Member;
import com.toy.badminton.domain.member.MemberCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.toy.badminton.domain.member.QMember.member;


@Repository
@RequiredArgsConstructor
public class MemberQuerydslRepository implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> findMembersByIds (List<Long> memberIds) {
        return queryFactory.selectFrom(member)
                .where(member.id.in(memberIds))
                .fetch();
    }
}
