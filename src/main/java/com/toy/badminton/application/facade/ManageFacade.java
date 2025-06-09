package com.toy.badminton.application.facade;

import com.toy.badminton.application.dto.param.ChangeGroupParameters;
import com.toy.badminton.application.dto.request.manager.ChangeGroupRequest;
import com.toy.badminton.application.dto.request.RoomParticipationRequest;
import com.toy.badminton.application.dto.request.manager.CustomGroupingRequest;
import com.toy.badminton.domain.factory.matching.MatchingFactory;
import com.toy.badminton.domain.factory.matching.MatchingType;
import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.domain.service.MatchGroupService;
import com.toy.badminton.domain.service.MatchingInfoService;
import com.toy.badminton.domain.service.MatchingRoomService;
import com.toy.badminton.domain.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.toy.badminton.domain.factory.matching.MatchService.DOUBLES;
import static com.toy.badminton.infrastructure.exception.ErrorCode.NOT_ENOUGH_MATCHING_MEMBERS;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManageFacade {
    private final MatchingRoomService matchingRoomService;
    private final MatchGroupService matchGroupService;
    private final MatchingInfoService matchingInfoService;
    private final MemberService memberService;
    private final MatchingFactory matchingFactory;


    @Transactional
    public void replaceMatchGroupMember(ChangeGroupParameters parameters) {
        MatchingRoom matchingRoom = matchingRoomService.findManageMatchingRoom(parameters.roomId(), parameters.member());
        Member replacementMember = memberService.findMember(parameters.replacementMemberId());
        Member targetMember = memberService.findMember(parameters.targetMemberId());
        matchingRoom.validateChangeRequestMembersExist(parameters.request());
        matchGroupService.replaceMatchGroupMember(parameters.groupId(), targetMember ,replacementMember);
        matchingRoom.changeMatchingStatus(replacementMember, MatchingStatus.MATCHED);
        matchingRoom.changeMatchingStatus(targetMember, MatchingStatus.WAITING);
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
    //todo 리펙토링 해보기 (cascade = CascadeType.PERSIST 여서 리스트안에 담기)
    public void startMatch(Long roomId, Member member, MatchingType type) {
        MatchingRoom matchingRoom = matchingRoomService.findManageMatchingRoom(roomId, member);

        List<MatchGroup> matchGroups = createMatchGroups(matchingRoom, type);
        Set<Member> matchedMembers = extractAllMembersFromMatchGroups(matchGroups);

        matchingInfoService.updateStatusToMatched(matchingRoom.getMatchingInfos(), matchedMembers);
    }

    private List<MatchGroup> createMatchGroups(MatchingRoom matchingRoom, MatchingType type) {
        return matchGroupService.savaAllMatchGroup(
                matchingFactory.getService(type).startMatching(matchingRoom)
        );
    }

    private Set<Member> extractAllMembersFromMatchGroups(List<MatchGroup> matchGroups) {
        return matchGroups.stream()
                .flatMap(group -> group.getMembers().stream())
                .collect(Collectors.toSet());
    }


    public void addManager(Long roomId, Long addMemberId, Member member) {
        Member addMember = memberService.findMember(addMemberId);
        matchingRoomService.addManagerRole(roomId, addMember, member);
    }

    public void removeManager(Long roomId, Long removeMemberId, Member member) {
        Member removeMember = memberService.findMember(removeMemberId);
        matchingRoomService.removeManagerRole(roomId, removeMember, member);
    }

    public void deactivateMatchingRoom(Long roomId, Member member) {
        matchingRoomService.deactivateMatchingRoom(roomId, member);
    }

    @Transactional
    public void customMatching(Long roomId, Member requestMember,CustomGroupingRequest request) {
        MatchingRoom matchingRoom = matchingRoomService.findManageMatchingRoom(roomId, requestMember);
        List<Member> members = memberService.findMembersByIds(request.memberIds());
        matchingRoom.addGroup(MatchGroup.createMatchGroup(matchingRoom, members));
    }

}
