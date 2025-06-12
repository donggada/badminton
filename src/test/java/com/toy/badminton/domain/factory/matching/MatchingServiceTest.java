package com.toy.badminton.domain.factory.matching;

import com.toy.badminton.domain.match.RandomMatchingService;
import com.toy.badminton.domain.member.Level;
import com.toy.badminton.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class MatchingServiceTest {

    @Test
    void sort() {
        RandomMatchingService randomMatchingService = new RandomMatchingService();
        Member member1 = Member.builder().id(1L).loginId("login1").username("철수").level(Level.MASTER).build();
        Member member2 = Member.builder().id(1L).loginId("login1").username("철수").level(Level.GROUP_A).build();
        Member member3 = Member.builder().id(1L).loginId("login1").username("철수").level(Level.GROUP_B).build();
        Member member4 = Member.builder().id(1L).loginId("login1").username("철수").level(Level.GROUP_C).build();

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