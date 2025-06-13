package com.toy.badminton.domain.match;

import com.toy.badminton.domain.member.Member;
import com.toy.badminton.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.toy.badminton.domain.match.MatchingStatus.*;
import static com.toy.badminton.infrastructure.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;

class MatchingRoomTest {

    @Test
    @DisplayName("validateMinActiveMembers: 최소 인원 충족 시 예외가 발생하지 않는다")
    void validateMinActiveMembers_sufficientMembers_noException() {
        Member member1 = Member.builder().id(1L).build();
        Member member2 = Member.builder().id(2L).build();
        Member member3 = Member.builder().id(3L).build();
        Member member4 = Member.builder().id(4L).build();

        List<MatchingRoomMember> matchingRoomMembers = List.of(
                MatchingRoomMember.builder().member(member1).status(WAITING).build(),
                MatchingRoomMember.builder().member(member2).status(WAITING).build(),
                MatchingRoomMember.builder().member(member3).status(WAITING).build(),
                MatchingRoomMember.builder().member(member4).status(WAITING).build()
        );

        MatchingRoom matchingRoom = MatchingRoom.builder().matchingRoomMembers(matchingRoomMembers).build();

        int minSize = 4;

        assertDoesNotThrow(() -> matchingRoom.validateMinActiveMembers(minSize));
    }


    @Test
    @DisplayName("validateMinActiveMembers: 최소 인원 미달 시 예외가 발생한다")
    void validateMinActiveMembers_insufficientMembers_throwsException() {
        Member member1 = Member.builder().id(1L).build();
        Member member2 = Member.builder().id(2L).build();
        Member member3 = Member.builder().id(3L).build();

        List<MatchingRoomMember> matchingRoomMembers = List.of(
                MatchingRoomMember.builder().member(member1).status(WAITING).build(),
                MatchingRoomMember.builder().member(member2).status(WAITING).build(),
                MatchingRoomMember.builder().member(member3).status(WAITING).build()
        );

        MatchingRoom matchingRoom = MatchingRoom.builder().matchingRoomMembers(matchingRoomMembers).build();

        int minSize = 4;

        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> matchingRoom.validateMinActiveMembers(minSize));


        assertEquals(NOT_ENOUGH_MATCHING_MEMBERS.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("validateManager: 매니저인 경우 예외가 발생하지 않는다")
    void validateManager_isManager_noException() {
        Member member = Member.builder().id(1L).build();
        MatchingRoom matchingRoom = MatchingRoom.builder().managerList(Set.of(member)).build();

        assertDoesNotThrow(() -> matchingRoom.validateManager(member));
    }

    @Test
    @DisplayName("validateManager: 매니저가 아닌 경우 예외가 발생한다")
    void validateManager_isNotManager_throwsException() {
        Member member = Member.builder().id(1L).build();
        MatchingRoom matchingRoom = MatchingRoom.builder().build();

        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> matchingRoom.validateManager(member));

        assertEquals(MATCHING_ROOM_EDIT_FORBIDDEN.getMessage(), exception.getMessage());
    }


    @Test
    @DisplayName("addMember: 새로운 멤버를 방에 추가한다")
    void addMember_addNewMember_addsToListAndSetsRoom() {
        Member member = Member.builder().id(1L).build();
        MatchingRoom matchingRoom = MatchingRoom.builder().build();
        MatchingRoomMember matchingRoomMember = MatchingRoomMember.builder().member(member).build();

        matchingRoom.addMember(member, matchingRoomMember);

        assertEquals(1, matchingRoom.getMatchingRoomMembers().size());
        assertTrue(matchingRoom.getMatchingRoomMembers().contains(matchingRoomMember));
    }

    @Test
    @DisplayName("addMember: 이미 있는 멤버를 다시 추가하면 상태만 WAITING으로 변경한다")
    void addMember_addExistingMember_changesStatusToWaiting() {
        Member member = Member.builder().id(1L).build();
        MatchingRoomMember matchingRoomMember = MatchingRoomMember.builder().member(member).build();
        MatchingRoomMember newMatchingRoomMember = MatchingRoomMember.builder().member(member).build();
        MatchingRoom matchingRoom = MatchingRoom.builder().matchingRoomMembers(List.of(matchingRoomMember)).build();

        matchingRoom.addMember(member, newMatchingRoomMember);

        assertEquals(1, matchingRoom.getMatchingRoomMembers().size());
        assertTrue(matchingRoom.getMatchingRoomMembers().contains(matchingRoomMember));
        assertFalse(matchingRoom.getMatchingRoomMembers().contains(newMatchingRoomMember));
        assertEquals(matchingRoomMember.getStatus(), WAITING);
    }



    @Test
    @DisplayName("addGroup: 그룹 멤버 수가 DOUBLES가 아닌 경우 예외가 발생한다")
    void addGroup_invalidMemberCount_throwsException() {
        Member member1 = Member.builder().id(1L).build();
        Member member2 = Member.builder().id(2L).build();
        Member member3 = Member.builder().id(3L).build();
        Member member4 = Member.builder().id(4L).build();
        List<Member> groupMembers = List.of(member1, member2, member3);
//        MatchGroup matchGroup = MatchGroup.builder().members(groupMembers).build();

        List<MatchingRoomMember> matchingRoomMembers = List.of(
                MatchingRoomMember.builder().member(member1).status(WAITING).build(),
                MatchingRoomMember.builder().member(member2).status(WAITING).build(),
                MatchingRoomMember.builder().member(member3).status(WAITING).build(),
                MatchingRoomMember.builder().member(member4).status(WAITING).build()
        );

        MatchingRoom matchingRoom = MatchingRoom.builder().matchingRoomMembers(matchingRoomMembers).build();


//        ApplicationException exception = assertThrows(ApplicationException.class,
//                () -> matchingRoom.addGroup(matchGroup));
//
//
//        assertEquals(NOT_ENOUGH_MATCHING_MEMBERS.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("addGroup: 그룹 멤버가 방에 없는 경우 예외가 발생한다")
    void addGroup_invalidGroupMemberAllInRoom_throwsException() {
        Member member1 = Member.builder().id(1L).build();
        Member member2 = Member.builder().id(2L).build();
        Member member3 = Member.builder().id(3L).build();
        Member member4 = Member.builder().id(4L).build();
        Member member5 = Member.builder().id(5L).build();
        List<Member> groupMembers = List.of(member1, member2, member3, member5);
//        MatchGroup matchGroup = MatchGroup.builder().members(groupMembers).build();

        List<MatchingRoomMember> matchingRoomMembers = List.of(
                MatchingRoomMember.builder().member(member1).status(WAITING).build(),
                MatchingRoomMember.builder().member(member2).status(WAITING).build(),
                MatchingRoomMember.builder().member(member3).status(WAITING).build(),
                MatchingRoomMember.builder().member(member4).status(WAITING).build()
        );

        MatchingRoom matchingRoom = MatchingRoom.builder().matchingRoomMembers(matchingRoomMembers).build();


//        ApplicationException exception = assertThrows(ApplicationException.class,
//                () -> matchingRoom.addGroup(matchGroup));


//        assertEquals(MEMBER_NOT_IN_ROOM.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("addGroup: 그룹 멤버 수가 DOUBLES 이고  그룹 멤버가 방에 있는 경우 예외하지 않고 상태값이 MATCHED 변경 한다")
    void addGroup_invalidGroupMemberAllInRoom_noException() {
        Member member1 = Member.builder().id(1L).build();
        Member member2 = Member.builder().id(2L).build();
        Member member3 = Member.builder().id(3L).build();
        Member member4 = Member.builder().id(4L).build();
        Member member5 = Member.builder().id(5L).build();
        List<Member> groupMembers = List.of(member1, member2, member3, member4);
//        MatchGroup matchGroup = MatchGroup.builder().members(groupMembers).build();
        MatchingRoomMember matchingRoomMember1 = MatchingRoomMember.builder().member(member1).status(WAITING).build();
        MatchingRoomMember matchingRoomMember2 = MatchingRoomMember.builder().member(member2).status(WAITING).build();
        MatchingRoomMember matchingRoomMember3 = MatchingRoomMember.builder().member(member3).status(WAITING).build();
        MatchingRoomMember matchingRoomMember4 = MatchingRoomMember.builder().member(member4).status(WAITING).build();
        MatchingRoomMember matchingRoomMember5 = MatchingRoomMember.builder().member(member5).status(WAITING).build();
        List<MatchingRoomMember> matchingRoomMembers = List.of(matchingRoomMember1, matchingRoomMember2, matchingRoomMember3, matchingRoomMember4);
        MatchingRoom matchingRoom = MatchingRoom.builder().matchingRoomMembers(matchingRoomMembers).build();

//        matchingRoom.addGroup(matchGroup);

//        assertEquals(matchingInfo1.getStatus(), MATCHED);
//        assertEquals(matchingInfo2.getStatus(), MATCHED);
//        assertEquals(matchingInfo3.getStatus(), MATCHED);
//        assertEquals(matchingInfo4.getStatus(), MATCHED);
//        assertEquals(matchingInfo5.getStatus(), WAITING);
    }

    @Test
    @DisplayName("addMangerRole: 방에 있는 멤버에게 매니저 권한 부여 성공")
    void addManagerRole_Success() {
        Member member1 = Member.builder().id(1L).build();
        MatchingRoomMember matchingRoomMember = MatchingRoomMember.builder().member(member1).status(WAITING).build();
        MatchingRoom matchingRoom = MatchingRoom.builder().matchingRoomMembers(List.of(matchingRoomMember)).build();
        matchingRoom.addMangerRole(member1);

        Set<Member> managerList = matchingRoom.getManagerList();
        assertEquals(managerList.size(), 1);
        assertTrue(managerList.contains(member1));
    }

    @Test
    @DisplayName("addMangerRole: 방에 없는 멤버에게 매니저 권한 부여 시 예외 발생")
    void addManagerRole_MemberNotInRoom_ThrowsException() {
        Member inMember = Member.builder().id(1L).build();
        Member notInMember = Member.builder().id(2L).build();
        MatchingRoomMember matchingRoomMember = MatchingRoomMember.builder().member(inMember).status(WAITING).build();
        MatchingRoom matchingRoom = MatchingRoom.builder().matchingRoomMembers(List.of(matchingRoomMember)).build();

        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> matchingRoom.addMangerRole(notInMember));


        assertEquals(MEMBER_NOT_IN_ROOM.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("addMangerRole: 이미 매니저인 멤버에게 매니저 권한 부여 시 중복 추가되지 않음")
    void addManagerRole_AlreadyManager_NoDuplicate() {
        Member member = Member.builder().id(1L).build();
        MatchingRoomMember matchingRoomMember = MatchingRoomMember.builder().member(member).status(WAITING).build();
        MatchingRoom matchingRoom = MatchingRoom.builder().matchingRoomMembers(List.of(matchingRoomMember)).build();

        matchingRoom.addMangerRole(member);

        Set<Member> managerList = matchingRoom.getManagerList();
        assertEquals(managerList.size(), 1);
        assertTrue(managerList.contains(member));
    }

    @Test
    @DisplayName("removeManagerRole: 방장이 방에 있는 매니저 권한 삭제 성공")
    void removeManagerRole_Success() {
        Member requestMember = Member.builder().id(1L).build();
        Member removeMember = Member.builder().id(2L).build();
        MatchingRoomMember matchingRoomMember1 = MatchingRoomMember.builder().member(requestMember).status(WAITING).build();
        MatchingRoomMember matchingRoomMember2 = MatchingRoomMember.builder().member(removeMember).status(WAITING).build();
        HashSet<Member> mockManagerList = new HashSet<>(Set.of(requestMember, removeMember));

        MatchingRoom matchingRoom = MatchingRoom.builder()
                .matchingRoomMembers(List.of(matchingRoomMember1, matchingRoomMember2))
                .managerList(mockManagerList)
                .build();

        matchingRoom.fixture(1L);

        matchingRoom.removeManagerRole(requestMember, removeMember);
        Set<Member> managerList = matchingRoom.getManagerList();
        assertEquals(managerList.size(), 1);
        assertTrue(managerList.contains(requestMember));
        assertFalse(managerList.contains(removeMember));
    }

    @Test
    @DisplayName("removeManagerRole: 방장이 아닌 멤버가 매니저 권한 삭제 시도 시 예외 발생")
    void removeManagerRole_RequesterNotOwner_ThrowsException() {
        Member requestMember = Member.builder().id(1L).build();
        Member removeMember = Member.builder().id(2L).build();
        MatchingRoomMember matchingRoomMember1 = MatchingRoomMember.builder().member(requestMember).status(WAITING).build();
        MatchingRoomMember matchingRoomMember2 = MatchingRoomMember.builder().member(removeMember).status(WAITING).build();
        HashSet<Member> mockManagerList = new HashSet<>(Set.of(requestMember, removeMember));

        MatchingRoom matchingRoom = MatchingRoom.builder()
                .matchingRoomMembers(List.of(matchingRoomMember1, matchingRoomMember2))
                .managerList(mockManagerList)
                .build();

        matchingRoom.fixture(2L);

        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> matchingRoom.removeManagerRole(requestMember, removeMember)
        );

        assertEquals(MANAGER_PERMISSION_DENIED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("removeManagerRole: 방장이 방에 없는 멤버의 매니저 권한 삭제 시도 시 예외 발생")
    void removeManagerRole_TargetMemberNotInRoom_ThrowsException() {
        Member requestMember = Member.builder().id(1L).build();
        Member removeMember = Member.builder().id(2L).build();
        Member member = Member.builder().id(3L).build();
        MatchingRoomMember matchingRoomMember1 = MatchingRoomMember.builder().member(requestMember).status(WAITING).build();
        MatchingRoomMember matchingRoomMember2 = MatchingRoomMember.builder().member(member).status(WAITING).build();
        HashSet<Member> mockManagerList = new HashSet<>(Set.of(requestMember, member));

        MatchingRoom matchingRoom = MatchingRoom.builder()
                .matchingRoomMembers(List.of(matchingRoomMember1, matchingRoomMember2))
                .managerList(mockManagerList)
                .build();

        matchingRoom.fixture(1L);

        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> matchingRoom.removeManagerRole(requestMember, removeMember)
        );

        assertEquals(MEMBER_NOT_IN_ROOM.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("removeManagerRole: 방장이 매니저가 아닌 멤버의 권한 삭제 시도 시 목록 변경 없음")
    void removeManagerRole_TargetMemberNotInManagerList_NoChange() {
        Member requestMember = Member.builder().id(1L).build();
        Member removeMember = Member.builder().id(2L).build();
        MatchingRoomMember matchingRoomMember1 = MatchingRoomMember.builder().member(requestMember).status(WAITING).build();
        MatchingRoomMember matchingRoomMember2 = MatchingRoomMember.builder().member(removeMember).status(WAITING).build();
        HashSet<Member> mockManagerList = new HashSet<>(Set.of(requestMember));

        MatchingRoom matchingRoom = MatchingRoom.builder()
                .matchingRoomMembers(List.of(matchingRoomMember1, matchingRoomMember2))
                .managerList(mockManagerList)
                .build();

        matchingRoom.fixture(1L);

        matchingRoom.removeManagerRole(requestMember, removeMember);
        Set<Member> managerList = matchingRoom.getManagerList();
        assertEquals(managerList.size(), 1);
        assertTrue(managerList.contains(requestMember));
        assertFalse(managerList.contains(removeMember));
    }

    @Test
    @DisplayName("deactivateMatchingRoom: 방장이 방을 비활성화한다")
    void deactivateMatchingRoom_ownerDeactivates_setsIsActiveToFalse() {
        Member requestMember = Member.builder().id(1L).build();

        MatchingRoom matchingRoom = MatchingRoom.builder()
                .isActive(true)
                .build();

        matchingRoom.fixture(1L);
        matchingRoom.deactivateMatchingRoom(requestMember);
        assertFalse(matchingRoom.isActive());
    }

    @Test
    @DisplayName("deactivateMatchingRoom: 방장이 아닌 멤버가 방 비활성화 시도 시 예외가 발생한다")
    void deactivateMatchingRoom_notOwnerAttemptsDeactivation_throwsException() {
        Member requestMember = Member.builder().id(1L).build();

        MatchingRoom matchingRoom = MatchingRoom.builder()
                .isActive(true)
                .build();

        matchingRoom.fixture(2L);

        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> matchingRoom.deactivateMatchingRoom(requestMember));

        assertEquals(MANAGER_PERMISSION_DENIED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("changeMatchingStatus: MatchingInfo가 있는 멤버의 상태 변경 성공")
    void changeMatchingStatus_MemberWithInfo_Success() {
        Member member = Member.builder().id(1L).build();
        MatchingRoomMember matchingRoomMember = MatchingRoomMember.builder().member(member).status(WAITING).build();
        MatchingRoom matchingRoom = MatchingRoom.builder()
                .matchingRoomMembers(List.of(matchingRoomMember))
                .build();

        matchingRoom.changeMatchingStatus(member, MATCHING_INACTIVE);

        assertEquals(matchingRoomMember.getStatus(), MATCHING_INACTIVE);
    }

    @Test
    @DisplayName("changeMatchingStatus: MatchingInfo가 없는 멤버의 상태 변경 시도 시 예외 발생")
    void changeMatchingStatus_MemberWithoutInfo_ThrowsException() {
        Member targetMember = Member.builder().id(1L).build();
        Member member = Member.builder().id(2L).build();
        MatchingRoomMember matchingRoomMember = MatchingRoomMember.builder().member(member).status(WAITING).build();
        MatchingRoom matchingRoom = MatchingRoom.builder()
                .matchingRoomMembers(List.of(matchingRoomMember))
                .build();

        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> matchingRoom.changeMatchingStatus(targetMember, MATCHING_INACTIVE)
        );

        assertEquals(INVALID_MATCHING_ROOM_INFO.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("findMembersByGroupId: 존재하는 groupId로 멤버 목록 조회 성공")
    void findMembersByGroupId_ExistingGroup_ReturnsMembers() {
        Long groupId = 1L;
        Member memberA = Member.builder().id(1L).build();
        Member memberB = Member.builder().id(2L).build();
        Member memberC = Member.builder().id(3L).build();
        Member memberD = Member.builder().id(4L).build();
        List<Member> members = List.of(memberA, memberB, memberC, memberD);
//        MatchGroup matchGroup = MatchGroup.builder()
//                .id(groupId)
//                .members(members)
//                .build();
//        MatchingRoom matchingRoom = MatchingRoom.builder().matchGroups(List.of(matchGroup)).build();

//        List<Member> result = matchingRoom.findMembersByGroupId(groupId);
//
//        assertEquals(result.size(), 4);
//        assertEquals(result, members);
    }

    @Test
    @DisplayName("findMembersByGroupId: 존재하지 않는 groupId로 멤버 목록 조회 시 예외 발생")
    void findMembersByGroupId_NonExistingGroup_ThrowsException() {
        Long groupId = 1L;
        Member memberA = Member.builder().id(1L).build();
        Member memberB = Member.builder().id(2L).build();
        Member memberC = Member.builder().id(3L).build();
        Member memberD = Member.builder().id(4L).build();
        List<Member> members = List.of(memberA, memberB, memberC, memberD);
//        MatchGroup matchGroup = MatchGroup.builder()
//                .id(groupId)
//                .members(members)
//                .build();
//        MatchingRoom matchingRoom = MatchingRoom.builder().matchGroups(List.of(matchGroup)).build();
//
//        ApplicationException exception = assertThrows(ApplicationException.class,
//                () -> matchingRoom.findMembersByGroupId(2L)
//        );
//
//        assertEquals(INVALID_MATCHING_GROUP.getMessage(), exception.getMessage());
    }


    @Test
    @DisplayName("replaceMatchGroupMember: 존재하는 그룹에서 멤버 교체 성공")
    void replaceMatchGroupMember_ExistingGroupAndMember_Success() {
        Long groupId = 1L;
        Member targetMember = Member.builder().id(1L).build();
        Member replacementMember = Member.builder().id(2L).build();
        Member memberA = Member.builder().id(3L).build();
        Member memberB = Member.builder().id(4L).build();
        Member memberC = Member.builder().id(5L).build();
        List<Member> members = new ArrayList<>(List.of(memberA, memberB, targetMember, memberC));
//        MatchGroup matchGroup = MatchGroup.builder().id(groupId).members(members).isGameOver(false).build();
//        MatchingRoom matchingRoom = MatchingRoom.builder().matchGroups(List.of(matchGroup)).build();
//
//        matchingRoom.replaceMatchGroupMember(groupId, targetMember, replacementMember);
//
//        assertEquals(members, List.of(memberA, memberB, replacementMember, memberC));
    }

    @Test
    @DisplayName("replaceMatchGroupMember: 존재하지 않는 groupId로 멤버 교체 시도 시 예외 발생")
    void replaceMatchGroupMember_NonExistingGroup_ThrowsException() {
        Long groupId = 1L;
        Member targetMember = Member.builder().id(1L).build();
        Member replacementMember = Member.builder().id(2L).build();
        Member memberA = Member.builder().id(3L).build();
        Member memberB = Member.builder().id(4L).build();
        Member memberC = Member.builder().id(5L).build();
        List<Member> members = new ArrayList<>(List.of(memberA, memberB, targetMember, memberC));
//        MatchGroup matchGroup = MatchGroup.builder().id(groupId).members(members).isGameOver(false).build();
//        MatchingRoom matchingRoom = MatchingRoom.builder().matchGroups(List.of(matchGroup)).build();
//
//        ApplicationException exception = assertThrows(ApplicationException.class,
//                () -> matchingRoom.replaceMatchGroupMember(2L, targetMember, replacementMember)
//        );
//
//        assertEquals(INVALID_MATCHING_GROUP.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("replaceMatchGroupMember: 존재하는 그룹이지만 교체 대상 멤버가 없는 경우 예외 발생")
    void replaceMatchGroupMember_ExistingGroupButTargetMemberNotFound_ThrowsException() {
        Long groupId = 1L;
        Member targetMember = Member.builder().id(1L).build();
        Member replacementMember = Member.builder().id(2L).build();
        Member memberA = Member.builder().id(3L).build();
        Member memberB = Member.builder().id(4L).build();
        Member memberC = Member.builder().id(5L).build();
        Member memberD = Member.builder().id(6L).build();
        List<Member> members = new ArrayList<>(List.of(memberA, memberB, memberC, memberD));
//        MatchGroup matchGroup = MatchGroup.builder().id(groupId).members(members).isGameOver(false).build();
//        MatchingRoom matchingRoom = MatchingRoom.builder().matchGroups(List.of(matchGroup)).build();
//
//        ApplicationException exception = assertThrows(ApplicationException.class,
//                () -> matchingRoom.replaceMatchGroupMember(groupId, targetMember, replacementMember)
//        );
//
//        assertEquals(TARGET_NOT_FOUND.getMessage(), exception.getMessage());
//        assertEquals(members, List.of(memberA, memberB, memberC, memberD));
    }

    @Test
    @DisplayName("replaceMatchGroupMember: 존재하는 그룹에서 교체할 멤버가 이미 그룹에 있는 경우 예외 발생")
    void replaceMatchGroupMember_ExistingGroupAndMember_ReplacementAlreadyInGroup_ThrowsException() {
        Long groupId = 1L;
        Member targetMember = Member.builder().id(1L).build();
        Member replacementMember = Member.builder().id(2L).build();
        Member memberA = Member.builder().id(3L).build();
        Member memberB = Member.builder().id(4L).build();
        List<Member> members = new ArrayList<>(List.of(memberA, memberB, targetMember, replacementMember));
//        MatchGroup matchGroup = MatchGroup.builder().id(groupId).members(members).isGameOver(false).build();
//        MatchingRoom matchingRoom = MatchingRoom.builder().matchGroups(List.of(matchGroup)).build();
//
//        ApplicationException exception = assertThrows(ApplicationException.class,
//                () -> matchingRoom.replaceMatchGroupMember(groupId, targetMember, replacementMember)
//        );
//
//        assertEquals(MEMBER_ALREADY_IN_GROUP.getMessage(), exception.getMessage());
//        assertEquals(members, List.of(memberA, memberB, targetMember, replacementMember));
    }


}