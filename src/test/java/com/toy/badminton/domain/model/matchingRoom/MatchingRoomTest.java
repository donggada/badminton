package com.toy.badminton.domain.model.matchingRoom;

import com.toy.badminton.application.dto.request.ChangeGroupRequest;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Level;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.infrastructure.exception.ApplicationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.toy.badminton.infrastructure.exception.ErrorCode.NOT_ENOUGH_MATCHING_MEMBERS;
import static org.junit.jupiter.api.Assertions.*;

class MatchingRoomTest {

    @Test
    @DisplayName("5명 중 5명 활성상태")
    void getActiveMembers1() {
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

        MatchingRoom testRoom = MatchingRoom.builder()
                .matchingInfos(List.of(matchingInfo1, matchingInfo2, matchingInfo3, matchingInfo4, matchingInfo5))
                .build();

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
        Member member1 = Member.builder().level(Level.MASTER).build();
        Member member2 = Member.builder().level(Level.GROUP_A).build();
        Member member3 = Member.builder().level(Level.GROUP_B).build();
        Member member4 = Member.builder().level(Level.GROUP_C).build();
        Member member5 = Member.builder().level(Level.GROUP_C).build();

        MatchingInfo matchingInfo1 = MatchingInfo.builder().member(member1).status(MatchingStatus.WAITING).build();
        MatchingInfo matchingInfo2 = MatchingInfo.builder().member(member2).status(MatchingStatus.WAITING).build();
        MatchingInfo matchingInfo3 = MatchingInfo.builder().member(member3).status(MatchingStatus.MATCHING_INACTIVE).build();
        MatchingInfo matchingInfo4 = MatchingInfo.builder().member(member4).status(MatchingStatus.MATCHING_INACTIVE).build();
        MatchingInfo matchingInfo5 = MatchingInfo.builder().member(member5).status(MatchingStatus.WAITING).build();

        MatchingRoom testRoom = MatchingRoom.builder()
                .matchingInfos(List.of(matchingInfo1, matchingInfo2, matchingInfo3, matchingInfo4, matchingInfo5))
                .build();

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
        Member member1 = Member.builder().level(Level.MASTER).build();
        Member member2 = Member.builder().level(Level.GROUP_A).build();
        Member member3 = Member.builder().level(Level.GROUP_B).build();
        Member member4 = Member.builder().level(Level.GROUP_C).build();
        Member member5 = Member.builder().level(Level.GROUP_C).build();


        MatchingInfo matchingInfo1 = MatchingInfo.builder().member(member1).status(MatchingStatus.WAITING).build();
        MatchingInfo matchingInfo2 = MatchingInfo.builder().member(member2).status(MatchingStatus.WAITING).build();
        MatchingInfo matchingInfo3 = MatchingInfo.builder().member(member3).status(MatchingStatus.MATCHING_INACTIVE).build();
        MatchingInfo matchingInfo4 = MatchingInfo.builder().member(member4).status(MatchingStatus.MATCHING_INACTIVE).build();
        MatchingInfo matchingInfo5 = MatchingInfo.builder().member(member5).status(MatchingStatus.WAITING).build();

        MatchingRoom testRoom = MatchingRoom.builder()
                .matchingInfos(List.of(matchingInfo1, matchingInfo2, matchingInfo3, matchingInfo4, matchingInfo5))
                .build();

        assertDoesNotThrow(() -> testRoom.validateMinActiveMembers(2));
    }

    @Test
    @DisplayName("단식 매칭 검증 2명 미만")
    void validateMinActiveMembers2() {
        Member member1 = Member.builder().level(Level.MASTER).build();
        Member member2 = Member.builder().level(Level.GROUP_A).build();
        Member member3 = Member.builder().level(Level.GROUP_B).build();
        Member member4 = Member.builder().level(Level.GROUP_C).build();
        Member member5 = Member.builder().level(Level.GROUP_C).build();

        MatchingInfo matchingInfo1 = MatchingInfo.builder().member(member1).status(MatchingStatus.WAITING).build();
        MatchingInfo matchingInfo2 = MatchingInfo.builder().member(member2).status(MatchingStatus.MATCHING_INACTIVE).build();
        MatchingInfo matchingInfo3 = MatchingInfo.builder().member(member3).status(MatchingStatus.MATCHING_INACTIVE).build();
        MatchingInfo matchingInfo4 = MatchingInfo.builder().member(member4).status(MatchingStatus.MATCHING_INACTIVE).build();
        MatchingInfo matchingInfo5 = MatchingInfo.builder().member(member5).status(MatchingStatus.MATCHING_INACTIVE).build();

        MatchingRoom testRoom = MatchingRoom.builder()
                .id(1L)
                .name("testRoom")
                .matchingInfos(List.of(matchingInfo1, matchingInfo2, matchingInfo3, matchingInfo4, matchingInfo5))
                .build();

        ApplicationException exception = assertThrows(ApplicationException.class, () -> testRoom.validateMinActiveMembers(2));
        assertEquals(exception.getMessage(), NOT_ENOUGH_MATCHING_MEMBERS.getMessage());
        assertEquals(exception.getReason(), NOT_ENOUGH_MATCHING_MEMBERS.getReason().formatted(2, 1));
    }

    @Test
    @DisplayName("복식 매칭 검증 4명 이상")
    void validateMinActiveMembers3() {
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

        MatchingRoom testRoom = MatchingRoom.builder()
                .id(1L)
                .name("testRoom")
                .matchingInfos(List.of(matchingInfo1, matchingInfo2, matchingInfo3, matchingInfo4, matchingInfo5))
                .build();

        assertDoesNotThrow(() -> testRoom.validateMinActiveMembers(4));

    }

    @Test
    @DisplayName("복식 매칭 검증 4명 미만")
    void validateMinActiveMembers4() {
        Member member1 = Member.builder().level(Level.MASTER).build();
        Member member2 = Member.builder().level(Level.GROUP_A).build();
        Member member3 = Member.builder().level(Level.GROUP_B).build();
        Member member4 = Member.builder().level(Level.GROUP_C).build();
        Member member5 = Member.builder().level(Level.GROUP_C).build();

        MatchingInfo matchingInfo1 = MatchingInfo.builder().member(member1).status(MatchingStatus.WAITING).build();
        MatchingInfo matchingInfo2 = MatchingInfo.builder().member(member2).status(MatchingStatus.WAITING).build();
        MatchingInfo matchingInfo3 = MatchingInfo.builder().member(member3).status(MatchingStatus.MATCHING_INACTIVE).build();
        MatchingInfo matchingInfo4 = MatchingInfo.builder().member(member4).status(MatchingStatus.MATCHING_INACTIVE).build();
        MatchingInfo matchingInfo5 = MatchingInfo.builder().member(member5).status(MatchingStatus.MATCHING_INACTIVE).build();

        MatchingRoom testRoom = MatchingRoom.builder()
                .matchingInfos(List.of(matchingInfo1, matchingInfo2, matchingInfo3, matchingInfo4, matchingInfo5))
                .build();

        ApplicationException exception = assertThrows(ApplicationException.class, () -> testRoom.validateMinActiveMembers(4));
        assertEquals(exception.getMessage(), NOT_ENOUGH_MATCHING_MEMBERS.getMessage());
        assertEquals(exception.getReason(), NOT_ENOUGH_MATCHING_MEMBERS.getReason().formatted(4, 2));
    }

    @Test
    @DisplayName("")
    void validateChangeRequestMembersExist () {
        ChangeGroupRequest request = new ChangeGroupRequest(1L, 1L, 2L);
        Member member1 = Member.builder().id(1L).build();
        Member member2 = Member.builder().id(2L).build();

        MatchingInfo matchingInfo1 = MatchingInfo.builder().member(member1).status(MatchingStatus.WAITING).build();
        MatchingInfo matchingInfo2 = MatchingInfo.builder().member(member2).status(MatchingStatus.WAITING).build();

        MatchingRoom room = MatchingRoom.builder()
                .matchingInfos(List.of(matchingInfo1, matchingInfo2))
                .build();

        assertDoesNotThrow(() -> room.validateChangeRequestMembersExist(request));
    }
}