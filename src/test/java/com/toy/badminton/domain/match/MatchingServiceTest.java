package com.toy.badminton.domain.match;

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
        Member member2 = Member.builder().id(2L).loginId("login2").username("영수").level(Level.GROUP_A).build();
        Member member3 = Member.builder().id(3L).loginId("login3").username("영희").level(Level.GROUP_B).build();
        Member member4 = Member.builder().id(4L).loginId("login4").username("보미").level(Level.GROUP_C).build();

        MatchingRoomMember matchingRoomMember1 = MatchingRoomMember.builder().member(member1).status(MatchingStatus.WAITING).build();
        MatchingRoomMember matchingRoomMember2 = MatchingRoomMember.builder().member(member2).status(MatchingStatus.WAITING).build();
        MatchingRoomMember matchingRoomMember3 = MatchingRoomMember.builder().member(member3).status(MatchingStatus.WAITING).build();
        MatchingRoomMember matchingRoomMember4 = MatchingRoomMember.builder().member(member4).status(MatchingStatus.WAITING).build();

        ArrayList<MatchingRoomMember> matchingRoomMembers = new ArrayList<>(List.of(matchingRoomMember1, matchingRoomMember2, matchingRoomMember3, matchingRoomMember4));

        List<MatchingRoomMember> result = randomMatchingService.sort(matchingRoomMembers);

        Assertions.assertThat(
                result
        ).containsExactly(matchingRoomMember1, matchingRoomMember4, matchingRoomMember2, matchingRoomMember3);
    }

}