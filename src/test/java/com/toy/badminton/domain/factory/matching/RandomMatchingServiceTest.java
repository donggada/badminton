package com.toy.badminton.domain.factory.matching;

import com.toy.badminton.domain.model.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.matchingInfo.MatchingStatus;
import com.toy.badminton.domain.model.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Level;
import com.toy.badminton.domain.model.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class RandomMatchingServiceTest {


    RandomMatchingService service = new RandomMatchingService();

    @Test
    @DisplayName("4명 대기명 인원있을경우")
    void startMatching1() {
        Member member1 = Member.fixture(1L, "login1", "", "카리나", "", Level.MASTER, new ArrayList<>());
        Member member2 = Member.fixture(2L, "login2", "", "윈터", "", Level.GROUP_A, new ArrayList<>());
        Member member3 = Member.fixture(3L, "login3", "", "닝닝", "", Level.GROUP_B, new ArrayList<>());
        Member member4 = Member.fixture(4L, "login4", "", "지절", "", Level.GROUP_C, new ArrayList<>());

        MatchingRoom testRoom = MatchingRoom.fixture(
                1L,
                "testRoom",
                List.of(),
                List.of()
        );
        MatchingInfo matchingInfo1 = MatchingInfo.fixture(null, testRoom, member1, MatchingStatus.WAITING, null);
        MatchingInfo matchingInfo2 = MatchingInfo.fixture(null, testRoom, member2, MatchingStatus.WAITING,null);
        MatchingInfo matchingInfo3 = MatchingInfo.fixture(null, testRoom, member3, MatchingStatus.WAITING,null);
        MatchingInfo matchingInfo4 = MatchingInfo.fixture(null, testRoom, member4, MatchingStatus.WAITING,null);

        MatchingRoom fixtureMatchingRoom= MatchingRoom.fixture(
                1L,
                "testRoom",
                List.of(matchingInfo1, matchingInfo2, matchingInfo3, matchingInfo4),
                List.of()
        );

        List<MatchGroup> result = service.startMatching(
                fixtureMatchingRoom
        );


        Assertions.assertThat(result).hasSize(1);

        Assertions.assertThat(
                result
        ).containsExactlyInAnyOrderElementsOf(
                List.of(MatchGroup.fixture(null, fixtureMatchingRoom, List.of(member1, member4, member2, member3)))
        );

    }


    @Test
    @DisplayName("5 ~ 7명 대기명 인원있을경우 6명")
    void startMatching2() {

        Member member1 = Member.fixture(1L, "login1", "", "슬기", "", Level.MASTER, new ArrayList<>());
        Member member2 = Member.fixture(2L, "login2", "", "아이린", "", Level.GROUP_A, new ArrayList<>());
        Member member3 = Member.fixture(3L, "login3", "", "조이", "", Level.GROUP_B, new ArrayList<>());
        Member member4 = Member.fixture(4L, "login4", "", "웬디", "", Level.GROUP_C, new ArrayList<>());
        Member member5 = Member.fixture(5L, "login5", "", "예리", "", Level.GROUP_C, new ArrayList<>());

        MatchingRoom testRoom = MatchingRoom.fixture(
                1L,
                "testRoom",
                List.of(),
                List.of()
        );
        MatchingInfo matchingInfo1 = MatchingInfo.fixture(null, testRoom, member1, MatchingStatus.WAITING, null);
        MatchingInfo matchingInfo2 = MatchingInfo.fixture(null, testRoom, member2, MatchingStatus.WAITING, null);
        MatchingInfo matchingInfo3 = MatchingInfo.fixture(null, testRoom, member3, MatchingStatus.WAITING, null);
        MatchingInfo matchingInfo4 = MatchingInfo.fixture(null, testRoom, member4, MatchingStatus.WAITING, null);
        MatchingInfo matchingInfo5 = MatchingInfo.fixture(null, testRoom, member5, MatchingStatus.WAITING, null);

        MatchingRoom fixtureMatchingRoom= MatchingRoom.fixture(
                1L,
                "testRoom",
                List.of(matchingInfo1, matchingInfo2, matchingInfo3, matchingInfo4, matchingInfo5),
                List.of()
        );

        List<MatchGroup> result = service.startMatching(
                fixtureMatchingRoom
        );


        Assertions.assertThat(result).hasSize(1);
    }



}