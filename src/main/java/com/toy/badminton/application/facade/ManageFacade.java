package com.toy.badminton.application.facade;

import com.toy.badminton.application.dto.param.ChangeGroupParameters;
import com.toy.badminton.application.dto.request.RoomParticipationRequest;
import com.toy.badminton.application.dto.request.manager.CustomGroupingRequest;
import com.toy.badminton.application.dto.request.manager.GroupStatusChangeRequest;
import com.toy.badminton.domain.factory.matching.MatchingFactory;
import com.toy.badminton.domain.factory.matching.MatchingType;
import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.domain.service.MatchGroupService;
import com.toy.badminton.domain.service.MatchingInfoService;
import com.toy.badminton.domain.service.MatchingRoomService;
import com.toy.badminton.domain.service.match.MatchService;
import com.toy.badminton.domain.service.member.MemberService;
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
    private final MatchService matchService;
    private final MemberService memberService;


    public void replaceMatchGroupMember(ChangeGroupParameters parameters) {
        Member replacementMember = memberService.findMember(parameters.replacementMemberId());
        Member targetMember = memberService.findMember(parameters.targetMemberId());
        matchService.changeMatchingGroupMemberStatus(parameters, targetMember, replacementMember);
    }


    public void participationRoom(Long roomId, Member member, RoomParticipationRequest request) {
        matchService.participationRoom(roomId, member, request.roomName());
    }

    public void startMatch(Long roomId, Member member, MatchingType type) {
        matchService.handleTypeMatching(roomId, member, type);
    }

    public void addManager(Long roomId, Long addMemberId, Member requestMember) {
        Member addMember = memberService.findMember(addMemberId);
        matchService.addManagerRole(roomId, addMember, requestMember);
    }

    public void removeManager(Long roomId, Long removeMemberId, Member requestMember) {
        Member removeMember = memberService.findMember(removeMemberId);
        matchService.removeManagerRole(roomId, removeMember, requestMember);
    }

    public void deactivateMatchingRoom(Long roomId, Member requestMember) {
        matchService.deactivateMatchingRoom(roomId, requestMember);
    }


    public void customMatching(Long roomId, Member requestMember, CustomGroupingRequest request) {
        List<Member> members = memberService.findMembersByIds(request.memberIds());
        matchService.handleCustomMatching(roomId, requestMember, members);
    }

    public void changeGroupStatus (Long roomId, Member requestMember, GroupStatusChangeRequest request) {
        matchService.changeGroupStatus(roomId, requestMember, request);
    }

}
