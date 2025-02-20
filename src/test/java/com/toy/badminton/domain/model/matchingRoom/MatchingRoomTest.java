package com.toy.badminton.domain.model.matchingRoom;

import com.toy.badminton.domain.model.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.matchingInfo.MatchingStatus;
import com.toy.badminton.domain.model.member.Level;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.infrastructure.exception.ApplicationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.toy.badminton.infrastructure.exception.ErrorCode.NOT_ENOUGH_MATCHING_MEMBERS;
import static org.junit.jupiter.api.Assertions.*;

class MatchingRoomTest {

    @Test
    @DisplayName("5명 중 5명 활성상태")
    void getActiveMembers1() {
        MatchingRoom fixtureRoom = MatchingRoom.fixture(
                1L,
                "testRoom",
                List.of(),
                List.of()
        );
        Member member1 = Member.fixture(1L, "login1", "", "슬기", "", Level.MASTER, new ArrayList<>());
        Member member2 = Member.fixture(2L, "login2", "", "아이린", "", Level.GROUP_A, new ArrayList<>());
        Member member3 = Member.fixture(3L, "login3", "", "조이", "", Level.GROUP_B, new ArrayList<>());
        Member member4 = Member.fixture(4L, "login4", "", "웬디", "", Level.GROUP_C, new ArrayList<>());
        Member member5 = Member.fixture(5L, "login5", "", "예리", "", Level.GROUP_C, new ArrayList<>());

        MatchingInfo matchingInfo1 = MatchingInfo.fixture(null, fixtureRoom, member1, MatchingStatus.WAITING, null);
        MatchingInfo matchingInfo2 = MatchingInfo.fixture(null, fixtureRoom, member2, MatchingStatus.WAITING, null);
        MatchingInfo matchingInfo3 = MatchingInfo.fixture(null, fixtureRoom, member3, MatchingStatus.WAITING, null);
        MatchingInfo matchingInfo4 = MatchingInfo.fixture(null, fixtureRoom, member4, MatchingStatus.WAITING, null);
        MatchingInfo matchingInfo5 = MatchingInfo.fixture(null, fixtureRoom, member5, MatchingStatus.WAITING, null);

        MatchingRoom testRoom= MatchingRoom.fixture(
                1L,
                "testRoom",
                List.of(matchingInfo1, matchingInfo2, matchingInfo3, matchingInfo4, matchingInfo5),
                List.of()
        );

        List<Member> result = testRoom.getActiveMembers();

        Assertions.assertThat(result).hasSize(5);

        Assertions.assertThat(
                result
        ).containsExactlyInAnyOrderElementsOf(
                List.of(member1, member2, member3, member4, member5)
        );
    }

    @Test
    @DisplayName("5명 중 3명 활성상태")
    void getActiveMembers2() {
        MatchingRoom fixtureRoom = MatchingRoom.fixture(
                1L,
                "testRoom",
                List.of(),
                List.of()
        );
        Member member1 = Member.fixture(1L, "login1", "", "슬기", "", Level.MASTER, new ArrayList<>());
        Member member2 = Member.fixture(2L, "login2", "", "아이린", "", Level.GROUP_A, new ArrayList<>());
        Member member3 = Member.fixture(3L, "login3", "", "조이", "", Level.GROUP_B, new ArrayList<>());
        Member member4 = Member.fixture(4L, "login4", "", "웬디", "", Level.GROUP_C, new ArrayList<>());
        Member member5 = Member.fixture(5L, "login5", "", "예리", "", Level.GROUP_C, new ArrayList<>());

        MatchingInfo matchingInfo1 = MatchingInfo.fixture(null, fixtureRoom, member1, MatchingStatus.WAITING,null);
        MatchingInfo matchingInfo2 = MatchingInfo.fixture(null, fixtureRoom, member2, MatchingStatus.WAITING,null);
        MatchingInfo matchingInfo3 = MatchingInfo.fixture(null, fixtureRoom, member3, MatchingStatus.MATCHING_INACTIVE,null);
        MatchingInfo matchingInfo4 = MatchingInfo.fixture(null, fixtureRoom, member4, MatchingStatus.MATCHING_INACTIVE,null);
        MatchingInfo matchingInfo5 = MatchingInfo.fixture(null, fixtureRoom, member5, MatchingStatus.WAITING, null);

        MatchingRoom testRoom= MatchingRoom.fixture(
                1L,
                "testRoom",
                List.of(matchingInfo1, matchingInfo2, matchingInfo3, matchingInfo4, matchingInfo5),
                List.of()
        );

        List<Member> result = testRoom.getActiveMembers();

        Assertions.assertThat(result).hasSize(3);

        Assertions.assertThat(
                result
        ).containsExactlyInAnyOrderElementsOf(
                List.of(member1, member2, member5)
        );
    }

    @Test
    @DisplayName("단식 매칭 검증 2명 이상")
    void validateMinActiveMembers1() {
        MatchingRoom fixtureRoom = MatchingRoom.fixture(
                1L,
                "testRoom",
                List.of(),
                List.of()
        );
        Member member1 = Member.fixture(1L, "login1", "", "슬기", "", Level.MASTER, new ArrayList<>());
        Member member2 = Member.fixture(2L, "login2", "", "아이린", "", Level.GROUP_A, new ArrayList<>());
        Member member3 = Member.fixture(3L, "login3", "", "조이", "", Level.GROUP_B, new ArrayList<>());
        Member member4 = Member.fixture(4L, "login4", "", "웬디", "", Level.GROUP_C, new ArrayList<>());
        Member member5 = Member.fixture(5L, "login5", "", "예리", "", Level.GROUP_C, new ArrayList<>());

        MatchingInfo matchingInfo1 = MatchingInfo.fixture(null, fixtureRoom, member1, MatchingStatus.WAITING,null);
        MatchingInfo matchingInfo2 = MatchingInfo.fixture(null, fixtureRoom, member2, MatchingStatus.WAITING,null);
        MatchingInfo matchingInfo3 = MatchingInfo.fixture(null, fixtureRoom, member3, MatchingStatus.MATCHING_INACTIVE,null);
        MatchingInfo matchingInfo4 = MatchingInfo.fixture(null, fixtureRoom, member4, MatchingStatus.MATCHING_INACTIVE,null);
        MatchingInfo matchingInfo5 = MatchingInfo.fixture(null, fixtureRoom, member5, MatchingStatus.WAITING, null);

        MatchingRoom testRoom= MatchingRoom.fixture(
                1L,
                "testRoom",
                List.of(matchingInfo1, matchingInfo2, matchingInfo3, matchingInfo4, matchingInfo5),
                List.of()
        );

        assertDoesNotThrow(() -> testRoom.validateMinActiveMembers(2));
    }

    @Test
    @DisplayName("단식 매칭 검증 2명 미만")
    void validateMinActiveMembers2() {
        MatchingRoom fixtureRoom = MatchingRoom.fixture(
                1L,
                "testRoom",
                List.of(),
                List.of()
        );
        Member member1 = Member.fixture(1L, "login1", "", "슬기", "", Level.MASTER, new ArrayList<>());
        Member member2 = Member.fixture(2L, "login2", "", "아이린", "", Level.GROUP_A, new ArrayList<>());
        Member member3 = Member.fixture(3L, "login3", "", "조이", "", Level.GROUP_B, new ArrayList<>());
        Member member4 = Member.fixture(4L, "login4", "", "웬디", "", Level.GROUP_C, new ArrayList<>());
        Member member5 = Member.fixture(5L, "login5", "", "예리", "", Level.GROUP_C, new ArrayList<>());

        MatchingInfo matchingInfo1 = MatchingInfo.fixture(null, fixtureRoom, member1, MatchingStatus.WAITING, null);
        MatchingInfo matchingInfo2 = MatchingInfo.fixture(null, fixtureRoom, member2, MatchingStatus.MATCHING_INACTIVE,null);
        MatchingInfo matchingInfo3 = MatchingInfo.fixture(null, fixtureRoom, member3, MatchingStatus.MATCHING_INACTIVE,null);
        MatchingInfo matchingInfo4 = MatchingInfo.fixture(null, fixtureRoom, member4, MatchingStatus.MATCHING_INACTIVE,null);
        MatchingInfo matchingInfo5 = MatchingInfo.fixture(null, fixtureRoom, member5, MatchingStatus.MATCHING_INACTIVE,null);

        MatchingRoom testRoom= MatchingRoom.fixture(
                1L,
                "testRoom",
                List.of(matchingInfo1, matchingInfo2, matchingInfo3, matchingInfo4, matchingInfo5),
                List.of()
        );

        ApplicationException exception = assertThrows(ApplicationException.class, () -> testRoom.validateMinActiveMembers(2));
        assertEquals(exception.getMessage(), NOT_ENOUGH_MATCHING_MEMBERS.getMessage());
        assertEquals(exception.getReason(), NOT_ENOUGH_MATCHING_MEMBERS.getReason().formatted(2, 1));
    }

    @Test
    @DisplayName("복식 매칭 검증 4명 이상")
    void validateMinActiveMembers3() {

        MatchingRoom fixtureRoom = MatchingRoom.fixture(
                1L,
                "testRoom",
                List.of(),
                List.of()
        );
        Member member1 = Member.fixture(1L, "login1", "", "슬기", "", Level.MASTER, new ArrayList<>());
        Member member2 = Member.fixture(2L, "login2", "", "아이린", "", Level.GROUP_A, new ArrayList<>());
        Member member3 = Member.fixture(3L, "login3", "", "조이", "", Level.GROUP_B, new ArrayList<>());
        Member member4 = Member.fixture(4L, "login4", "", "웬디", "", Level.GROUP_C, new ArrayList<>());
        Member member5 = Member.fixture(5L, "login5", "", "예리", "", Level.GROUP_C, new ArrayList<>());

        MatchingInfo matchingInfo1 = MatchingInfo.fixture(null, fixtureRoom, member1, MatchingStatus.WAITING,null);
        MatchingInfo matchingInfo2 = MatchingInfo.fixture(null, fixtureRoom, member2, MatchingStatus.WAITING,null);
        MatchingInfo matchingInfo3 = MatchingInfo.fixture(null, fixtureRoom, member3, MatchingStatus.WAITING,null);
        MatchingInfo matchingInfo4 = MatchingInfo.fixture(null, fixtureRoom, member4, MatchingStatus.WAITING,null);
        MatchingInfo matchingInfo5 = MatchingInfo.fixture(null, fixtureRoom, member5, MatchingStatus.WAITING, null);

        MatchingRoom testRoom= MatchingRoom.fixture(
                1L,
                "testRoom",
                List.of(matchingInfo1, matchingInfo2, matchingInfo3, matchingInfo4, matchingInfo5),
                List.of()
        );

        assertDoesNotThrow(() -> testRoom.validateMinActiveMembers(4));

    }

    @Test
    @DisplayName("복식 매칭 검증 4명 미만")
    void validateMinActiveMembers4() {
        MatchingRoom fixtureRoom = MatchingRoom.fixture(
                1L,
                "testRoom",
                List.of(),
                List.of()
        );
        Member member1 = Member.fixture(1L, "login1", "", "슬기", "", Level.MASTER, new ArrayList<>());
        Member member2 = Member.fixture(2L, "login2", "", "아이린", "", Level.GROUP_A, new ArrayList<>());
        Member member3 = Member.fixture(3L, "login3", "", "조이", "", Level.GROUP_B, new ArrayList<>());
        Member member4 = Member.fixture(4L, "login4", "", "웬디", "", Level.GROUP_C, new ArrayList<>());
        Member member5 = Member.fixture(5L, "login5", "", "예리", "", Level.GROUP_C, new ArrayList<>());

        MatchingInfo matchingInfo1 = MatchingInfo.fixture(null, fixtureRoom, member1, MatchingStatus.WAITING, null);
        MatchingInfo matchingInfo2 = MatchingInfo.fixture(null, fixtureRoom, member2, MatchingStatus.WAITING, null);
        MatchingInfo matchingInfo3 = MatchingInfo.fixture(null, fixtureRoom, member3, MatchingStatus.MATCHING_INACTIVE,null);
        MatchingInfo matchingInfo4 = MatchingInfo.fixture(null, fixtureRoom, member4, MatchingStatus.MATCHING_INACTIVE,null);
        MatchingInfo matchingInfo5 = MatchingInfo.fixture(null, fixtureRoom, member5, MatchingStatus.MATCHING_INACTIVE,null);

        MatchingRoom testRoom= MatchingRoom.fixture(
                1L,
                "testRoom",
                List.of(matchingInfo1, matchingInfo2, matchingInfo3, matchingInfo4, matchingInfo5),
                List.of()
        );

        ApplicationException exception = assertThrows(ApplicationException.class, () -> testRoom.validateMinActiveMembers(4));
        assertEquals(exception.getMessage(), NOT_ENOUGH_MATCHING_MEMBERS.getMessage());
        assertEquals(exception.getReason(), NOT_ENOUGH_MATCHING_MEMBERS.getReason().formatted(4, 2));
    }
}