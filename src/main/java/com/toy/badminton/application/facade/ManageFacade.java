package com.toy.badminton.application.facade;

import com.toy.badminton.application.dto.request.ChangeGroupRequest;
import com.toy.badminton.application.dto.request.RoomParticipationRequest;
import com.toy.badminton.application.dto.response.RoomParticipationResponse;
import com.toy.badminton.domain.factory.matching.MatchingFactory;
import com.toy.badminton.domain.factory.matching.MatchingType;
import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.domain.service.MatchGroupService;
import com.toy.badminton.domain.service.MatchingInfoService;
import com.toy.badminton.domain.service.MatchingRoomService;
import com.toy.badminton.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManageFacade {
    private final MatchingRoomService matchingRoomService;
    private final MatchGroupService matchGroupService;
    private final MatchingInfoService matchingInfoService;
    private final MemberService memberService;
    private final MatchingFactory matchingFactory;


    public void replaceMatchGroupMember(Long roomId, Member member, ChangeGroupRequest request) {
        MatchingRoom matchingRoom = matchingRoomService.findManageMatchingRoom(roomId, member);
        matchingRoom.validateChangeRequestMembersExist(request);
        Member replacementMember = memberService.findMember(request.replacementMemberId());
        Member targetMember = memberService.findMember(request.targetMemberId());
        matchGroupService.replaceMatchGroupMember(request.groupId(), targetMember ,replacementMember);
    }

    @Transactional
    public void participationRoom(Long roomId, Member member, RoomParticipationRequest request) {
        Set<Member> newManagers = getManagerMembersFromIds(request.managerIdList());
        MatchingRoom manageMatchingRoom = matchingRoomService.findManageMatchingRoom(roomId, member);
        manageMatchingRoom.updateRoomInfo(request.roomName(), newManagers);
    }

    private Set<Member> getManagerMembersFromIds(Set<Long> managerIds) {
        return managerIds.stream()
                .map(memberService::findMember)
                .collect(Collectors.toSet());
    }

    @Transactional
    public void startMatch(Long roomId, Member member, MatchingType type) {
        MatchingRoom matchingRoom = matchingRoomService.findManageMatchingRoom(roomId, member);

        List<MatchGroup> matchGroups = matchGroupService.savaAllMatchGroup(
                matchingFactory.getService(type).startMatching(matchingRoom)
        );

        Set<Member> memberSet = matchGroups.stream()
                .flatMap(group -> group.getMembers().stream())
                .collect(Collectors.toSet());

        matchingInfoService.changeStatusByMatched(matchingRoom.getMatchingInfos(), memberSet);
    }
}
