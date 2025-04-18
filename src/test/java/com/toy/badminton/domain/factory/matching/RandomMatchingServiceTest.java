package com.toy.badminton.domain.factory.matching;

import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Level;
import com.toy.badminton.domain.model.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class RandomMatchingServiceTest {


    RandomMatchingService service = new RandomMatchingService();

    @Test
    @DisplayName("4명 대기명 인원있을경우")
    void startMatching1() {
        Member member1 = Member.builder().level(Level.MASTER).build();
        Member member2 = Member.builder().level(Level.GROUP_A).build();
        Member member3 = Member.builder().level(Level.GROUP_B).build();
        Member member4 = Member.builder().level(Level.GROUP_C).build();

        MatchingInfo matchingInfo1 = MatchingInfo.builder().member(member1).status(MatchingStatus.WAITING).build();
        MatchingInfo matchingInfo2 = MatchingInfo.builder().member(member2).status(MatchingStatus.WAITING).build();
        MatchingInfo matchingInfo3 = MatchingInfo.builder().member(member3).status(MatchingStatus.WAITING).build();
        MatchingInfo matchingInfo4 = MatchingInfo.builder().member(member4).status(MatchingStatus.WAITING).build();

        MatchingRoom matchingRoom = MatchingRoom.builder().matchingInfos(List.of(matchingInfo1, matchingInfo2, matchingInfo3, matchingInfo4)).build();

        List<MatchGroup> result = service.startMatching(
                matchingRoom
        );


        Assertions.assertThat(result).hasSize(1);

        Assertions.assertThat(
                result
        ).containsExactlyInAnyOrderElementsOf(
                List.of(MatchGroup.builder().matchingRoom(matchingRoom).members(List.of(member1, member4, member2, member3)).build())
        );

    }


    @Test
    @DisplayName("5 ~ 7명 대기명 인원있을경우 6명")
    void startMatching2() {
        Member member1 = Member.builder().level(Level.MASTER).build();
        Member member2 = Member.builder().level(Level.GROUP_A).build();
        Member member3 = Member.builder().level(Level.GROUP_B).build();
        Member member4 = Member.builder().level(Level.GROUP_C).build();
        Member member5 = Member.builder().level(Level.GROUP_C).build();

        MatchingInfo matchingInfo1 = MatchingInfo.builder().member(member1).status(MatchingStatus.WAITING).build();
        MatchingInfo matchingInfo2 = MatchingInfo.builder().member(member2).status(MatchingStatus.WAITING).build();
        MatchingInfo matchingInfo3 = MatchingInfo.builder().member(member3).status(MatchingStatus.WAITING).build();
        MatchingInfo matchingInfo4 = MatchingInfo.builder().member(member4).status(MatchingStatus.WAITING).build();
        MatchingInfo matchingInfo5 = MatchingInfo.builder().member(member5).status(MatchingStatus.WAITING).build();


        MatchingRoom matchingRoom = MatchingRoom.builder()
                .matchingInfos(List.of(matchingInfo1, matchingInfo2, matchingInfo3, matchingInfo4, matchingInfo5))
                .build();

        List<MatchGroup> result = service.startMatching(
                matchingRoom
        );


        Assertions.assertThat(result).hasSize(1);
    }



}