package com.toy.badminton.application.facade;

import com.toy.badminton.application.dto.param.ChangeGroupParameters;
import com.toy.badminton.application.dto.request.manager.ChangeGroupRequest;
import com.toy.badminton.application.dto.request.RoomParticipationRequest;
import com.toy.badminton.domain.factory.matching.BalancedMatchingService;
import com.toy.badminton.domain.factory.matching.MatchingFactory;
import com.toy.badminton.domain.factory.matching.MatchingType;
import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoomRepository;
import com.toy.badminton.domain.model.member.Level;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.domain.service.MatchGroupService;
import com.toy.badminton.domain.service.MatchingInfoService;
import com.toy.badminton.domain.service.MatchingRoomService;
import com.toy.badminton.domain.service.member.MemberServiceImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ManageFacadeTest {
    @InjectMocks
    private ManageFacade manageFacade;

    @Mock
    private MatchingRoomService matchingRoomService;
    @Mock
    private MemberServiceImp memberServiceImp;
    @Mock
    private MatchGroupService matchGroupService;
    @Mock
    private MatchingFactory matchingFactory;
    @Mock
    private MatchingInfoService matchingInfoService;

    @Mock
    private MatchingRoomRepository matchingRoomRepository;

    @Test
    @DisplayName("replaceMatchGroupMember_정상적으로호출된다()")
    void replaceMatchGroupMember() {
        Long roomId = 1L;
        Long targetMemberId = 100L;
        Long replacementMemberId = 200L;
        Long groupId = 10L;
        Member member = Member.builder().id(1L).build();
        Member targetMember = Member.builder().id(targetMemberId).build();
        Member replacementMember = Member.builder().id(replacementMemberId).build();
        ChangeGroupRequest request = new ChangeGroupRequest(replacementMemberId, targetMemberId);

        List<MatchingInfo> matchingInfos = List.of(
                MatchingInfo.builder().member(member).build(),
                MatchingInfo.builder().member(targetMember).build(),
                MatchingInfo.builder().member(replacementMember).build()
        );
        MatchingRoom matchingRoom = MatchingRoom.builder().matchingInfos(matchingInfos).build();

        given(matchingRoomService.findManageMatchingRoom(roomId, member)).willReturn(matchingRoom);
        given(memberServiceImp.findMember(targetMemberId)).willReturn(targetMember);
        given(memberServiceImp.findMember(replacementMemberId)).willReturn(replacementMember);

        ChangeGroupParameters parameters = ChangeGroupParameters.builder()
                .roomId(roomId)
                .groupId(groupId)
                .member(member)
                .request(request)
                .build();

        manageFacade.replaceMatchGroupMember(parameters);

        verify(matchGroupService).replaceMatchGroupMember(groupId, targetMember, replacementMember);
    }



    @Test
    @DisplayName("방 참여 요청 시 방 이름을 업데이트하고 새로운 관리자를 추가한다.")
    void participationRoom () {
        Long roomId = 1L;
        Member member = Member.builder().id(1L).build();
        Member member1 = Member.builder().id(2L).build();
        Member member2 = Member.builder().id(3L).build();
        Member member4 = Member.builder().id(4L).build();
        HashSet<Member> managers = new HashSet<>();
        managers.add(member);

        MatchingRoom matchingRoom = MatchingRoom.builder().id(roomId).name("없음").managerList(managers).build();

        RoomParticipationRequest request = new RoomParticipationRequest("테스트", Set.of(2L, 3L));
        given(memberServiceImp.findMember(2L)).willReturn(member1);
        given(memberServiceImp.findMember(3L)).willReturn(member2);
        given(matchingRoomService.findManageMatchingRoom(roomId, member)).willReturn(matchingRoom);

        manageFacade.participationRoom(roomId, member, request);
        assertEquals(matchingRoom.getName(), "테스트");
        assertEquals(matchingRoom.getManagerList().size(), 3);
        assertTrue(matchingRoom.getManagerList().contains(member));
        assertTrue(matchingRoom.getManagerList().contains(member1));
        assertTrue(matchingRoom.getManagerList().contains(member2));
        assertFalse(matchingRoom.getManagerList().contains(member4));
    }

    @Test
    @DisplayName("매칭을 시작하면 매칭 그룹을 생성하고, 매칭된 멤버들의 상태를 업데이트한다.")
    void startMatch() {
        Long roomId = 1L;
        Member member = Member.builder().id(1L).level(Level.GROUP_A).build();
        MatchingType type = MatchingType.BALANCED; // 랜덤은 테스트 불가

        // 멤버들 설정
        Member member1 = Member.builder().id(2L).level(Level.MASTER).build();
        Member member2 = Member.builder().id(3L).level(Level.GROUP_C).build();
        Member member3 = Member.builder().id(4L).level(Level.GROUP_B).build();
        List<MatchingInfo> matchingInfos = new ArrayList<>();
        matchingInfos.add(MatchingInfo.builder().member(member).status(MatchingStatus.WAITING).build());
        matchingInfos.add(MatchingInfo.builder().member(member1).status(MatchingStatus.WAITING).build());
        matchingInfos.add(MatchingInfo.builder().member(member2).status(MatchingStatus.WAITING).build());
        matchingInfos.add(MatchingInfo.builder().member(member3).status(MatchingStatus.WAITING).build());


        MatchingRoom matchingRoom = MatchingRoom.builder()
                .id(roomId)
                .matchingInfos(matchingInfos)
                .build();
        List<MatchGroup> groups = new ArrayList<>();
        groups.add(
                MatchGroup.builder()
                        .matchingRoom(matchingRoom)
                        .members(List.of(member1, member2, member, member3))
                        .build()
        );

        given(matchingRoomService.findManageMatchingRoom(roomId,member)).willReturn(matchingRoom);

        given(matchGroupService.savaAllMatchGroup(groups)).willReturn(groups);
        given(matchingFactory.getService(type)).willReturn(new BalancedMatchingService());

        manageFacade.startMatch(roomId, member, type);
        verify(matchingInfoService).updateStatusToMatched(eq(matchingInfos), eq(Set.of(member1, member2, member3, member)));
    }

}