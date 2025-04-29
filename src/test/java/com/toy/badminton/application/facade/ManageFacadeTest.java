package com.toy.badminton.application.facade;

import com.toy.badminton.application.dto.request.ChangeGroupRequest;
import com.toy.badminton.domain.factory.matching.MatchingFactory;
import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.match.matchGroup.MatchGroupRepository;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.domain.service.MatchGroupService;
import com.toy.badminton.domain.service.MatchingInfoService;
import com.toy.badminton.domain.service.MatchingRoomService;
import com.toy.badminton.domain.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ManageFacadeTest {
    @InjectMocks
    private ManageFacade manageFacade;
    @Mock
    private MatchingRoomService matchingRoomService;
    @Mock
    private MatchGroupService matchGroupService;
    @Mock
    private MatchingInfoService matchingInfoService;
    @Mock
    private MemberService memberService;
    @Mock
    private MatchingFactory matchingFactory;
    @Mock
    private MatchGroupRepository matchGroupRepository;


    @Test
    void replaceMatchGroupMember() {
        Long roomId = 1L;
        Long groupId = 1L;
        Long replacementMemberId = 2L;
        Long targetMemberId = 3L;

        Member member = Member.builder().id(1L).build();
        Member member1 = Member.builder().id(3L).build();
        Member member2 = Member.builder().id(4L).build();
        Member member3 = Member.builder().id(5L).build();
        Member member4 = Member.builder().id(6L).build();
        Member replacementMember = Member.builder().id(replacementMemberId).build();
        Member targetMember = Member.builder().id(targetMemberId).build();

        List<Member> members = new ArrayList<>();
        members.add(member1);
        members.add(member2);
        members.add(member3);
        members.add(member4);
        List<MatchingInfo> matchingInfos = new ArrayList<>();
        matchingInfos.add(MatchingInfo.builder().member(member).build());
        matchingInfos.add(MatchingInfo.builder().member(member1).build());
        matchingInfos.add(MatchingInfo.builder().member(member2).build());
        matchingInfos.add(MatchingInfo.builder().member(member3).build());
        matchingInfos.add(MatchingInfo.builder().member(member4).build());
        matchingInfos.add(MatchingInfo.builder().member(replacementMember).build());

        List<MatchGroup> matchGroups = new ArrayList<>();
        MatchGroup matchGroup = MatchGroup.builder().id(groupId).members(members).build();
        matchGroups.add(matchGroup);

        MatchingRoom matchingRoom = MatchingRoom.builder()
                .id(roomId)
                .matchGroups(matchGroups)
                .matchingInfos(matchingInfos)
                .managerList(Set.of(member))
                .build();

        ChangeGroupRequest request = new ChangeGroupRequest(groupId, replacementMemberId, targetMemberId);

//        given(matchingRoomService.findManageMatchingRoom(roomId, member)).willReturn(matchingRoom);
//        given(memberService.findMember(replacementMemberId)).willReturn(replacementMember);
//        given(memberService.findMember(targetMemberId)).willReturn(targetMember);
//        given(matchGroupRepository.findById(groupId)).willCallRealMethod();
//        doNothing().when(matchGroupService).replaceMatchGroupMember(groupId, targetMember, replacementMember);

//        manageFacade.replaceMatchGroupMember(roomId, member, request);

//        assertFalse(matchGroups.contains(targetMember));
//        assertTrue(matchGroups.contains(replacementMember));
    }
}