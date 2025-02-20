package com.toy.badminton.domain.factory.matching;

import com.toy.badminton.domain.model.member.Level;
import com.toy.badminton.domain.model.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class MatchServiceTest {

    @Test
    void sort() {
        RandomMatchingService randomMatchingService = new RandomMatchingService();

        Member member1 = Member.fixture(1L, "login1", "", "철수", "", Level.MASTER, new ArrayList<>());
        Member member2 = Member.fixture(2L, "login2", "", "영희", "", Level.GROUP_A, new ArrayList<>());
        Member member3 = Member.fixture(3L, "login3", "", "복희", "", Level.GROUP_B, new ArrayList<>());
        Member member4 = Member.fixture(4L, "login4", "", "첨지", "", Level.GROUP_C, new ArrayList<>());

        ArrayList<Member> memberList = new ArrayList<>();
        memberList.add(member1);
        memberList.add(member2);
        memberList.add(member3);
        memberList.add(member4);
        List<Member> result = randomMatchingService.sort(memberList);

        Assertions.assertThat(
                result
        ).containsExactly(member1, member4, member2, member3);
    }

}