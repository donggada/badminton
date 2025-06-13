package com.toy.badminton.domain.match;

import com.toy.badminton.domain.member.Level;
import com.toy.badminton.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class BalancedMatchingServiceTest {
    BalancedMatchingService service = new BalancedMatchingService();

    @Test
    @DisplayName("4명 대기명 인원있을경우")
    void startMatching1() {

        Member member1 = Member.builder().level(Level.MASTER).build();
        Member member2 = Member.builder().level(Level.GROUP_A).build();
        Member member3 = Member.builder().level(Level.GROUP_B).build();
        Member member4 = Member.builder().level(Level.GROUP_C).build();

        MatchingRoomMember matchingRoomMember1 = MatchingRoomMember.builder().member(member1).status(MatchingStatus.WAITING).build();
        MatchingRoomMember matchingRoomMember2 = MatchingRoomMember.builder().member(member2).status(MatchingStatus.WAITING).build();
        MatchingRoomMember matchingRoomMember3 = MatchingRoomMember.builder().member(member3).status(MatchingStatus.WAITING).build();
        MatchingRoomMember matchingRoomMember4 = MatchingRoomMember.builder().member(member4).status(MatchingStatus.WAITING).build();

        MatchingRoom matchingRoom= MatchingRoom.builder().matchingRoomMembers(List.of(matchingRoomMember1, matchingRoomMember2, matchingRoomMember3, matchingRoomMember4)).build();

        List<MatchGroup> result = service.startMatching(matchingRoom);


        Assertions.assertThat(result).hasSize(1);
//        Assertions.assertThat(
//                result
//        ).containsExactlyInAnyOrderElementsOf(
//                List.of(MatchGroup.builder().matchingRoom(matchingRoom).matchingRoomMembers(List.of(member1, member4, member2, member3)).build())
//        );
    }

    @Test
    @DisplayName("5 ~ 7명 대기명 인원있을경우 6명")
    void startMatching2() {
        Member member1 = Member.builder().level(Level.MASTER).build();
        Member member2 = Member.builder().level(Level.GROUP_A).build();
        Member member3 = Member.builder().level(Level.GROUP_B).build();
        Member member4 = Member.builder().level(Level.GROUP_C).build();
        Member member5 = Member.builder().level(Level.GROUP_C).build();

        MatchingRoomMember matchingRoomMember1 = MatchingRoomMember.builder().member(member1).status(MatchingStatus.WAITING).build();
        MatchingRoomMember matchingRoomMember2 = MatchingRoomMember.builder().member(member2).status(MatchingStatus.WAITING).build();
        MatchingRoomMember matchingRoomMember3 = MatchingRoomMember.builder().member(member3).status(MatchingStatus.WAITING).build();
        MatchingRoomMember matchingRoomMember4 = MatchingRoomMember.builder().member(member4).status(MatchingStatus.WAITING).build();
        MatchingRoomMember matchingRoomMember5 = MatchingRoomMember.builder().member(member5).status(MatchingStatus.WAITING).build();


        MatchingRoom matchingRoom= MatchingRoom.builder().matchingRoomMembers(List.of(matchingRoomMember1, matchingRoomMember2, matchingRoomMember3, matchingRoomMember4, matchingRoomMember5)).build();

        List<MatchGroup> result = service.startMatching(matchingRoom);

        Assertions.assertThat(result).hasSize(1);

//        Assertions.assertThat(
//                result
//        ).containsExactlyInAnyOrderElementsOf(
//                List.of(MatchGroup.builder().matchingRoom(matchingRoom).matchingRoomMembers(List.of(member1, member4, member2, member3)).build())
//        );
    }
}