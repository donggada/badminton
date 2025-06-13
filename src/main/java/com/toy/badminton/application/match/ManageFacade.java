package com.toy.badminton.application.match;

import com.toy.badminton.api.member.MemberServiceApi;
import com.toy.badminton.domain.match.*;
import com.toy.badminton.domain.member.Member;
import com.toy.badminton.presentation.match.request.CustomGroupingRequest;
import com.toy.badminton.presentation.match.request.GroupStatusChangeRequest;
import com.toy.badminton.presentation.match.request.RoomParticipationRequest;
import com.toy.badminton.presentation.match.vo.ChangeGroupParameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.toy.badminton.domain.match.MatchingStatus.MATCHED;
import static com.toy.badminton.domain.match.MatchingStatus.WAITING;
import static com.toy.badminton.infrastructure.exception.ErrorCode.REQUESTER_NOT_FOUND;
import static com.toy.badminton.infrastructure.exception.ErrorCode.TARGET_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManageFacade {
    private final MatchService matchService;
    private final MemberServiceApi memberServiceApi;

    @Transactional
    public void replaceMatchGroupMember(ChangeGroupParameters parameters) {
        Member replacementMember = memberServiceApi.findMember(parameters.replacementMemberId());
        Member targetMember = memberServiceApi.findMember(parameters.targetMemberId());
        MatchingRoom matchingRoom = matchService.findManageMatchingRoom(parameters.roomId(), parameters.member());
        MatchingRoomMember replacementMatchingRoomMember = matchingRoom.findMatchingRoomMemberByMember(replacementMember).orElseThrow(() -> REQUESTER_NOT_FOUND.build(replacementMember.getId()));
        MatchingRoomMember targetMatchingRoomMember = matchingRoom.findMatchingRoomMemberByMember(targetMember).orElseThrow(() -> TARGET_NOT_FOUND.build(targetMember.getId()));

        matchingRoom.replaceMatchGroupMember(parameters.groupId(), targetMatchingRoomMember, replacementMatchingRoomMember);
        matchingRoom.changeMatchingStatus(replacementMember, MATCHED);
        matchingRoom.changeMatchingStatus(targetMember, WAITING);
    }

    @Transactional
    public void participationRoom(Long roomId, Member requestMember, RoomParticipationRequest request) {
        MatchingRoom matchingRoom = matchService.findManageMatchingRoom(roomId, requestMember);

        matchingRoom.updateRoomName(request.roomName());
    }

    @Transactional
    public void startMatch(Long roomId, Member requestMember, MatchingType type) {
        MatchingRoom matchingRoom = matchService.findManageMatchingRoom(roomId, requestMember);
        List<MatchGroup> matchGroups = matchService.createMatchGroups(matchingRoom, type);

        matchGroups.forEach(matchGroup -> processAddGroup(matchingRoom, matchGroup));
    }

    private void processAddGroup(MatchingRoom matchingRoom, MatchGroup addMatchGroup) {
        try {
            matchingRoom.addGroup(addMatchGroup);
        } catch (Exception  e) {
            log.error("processAddGroup error {}, MatchingRoom = {}, MatchGroup = {}", e.getMessage(), matchingRoom, addMatchGroup, e);
        }
    }

    @Transactional
    public void addManager(Long roomId, Long addMemberId, Member requestMember) {
        Member addMember = memberServiceApi.findMember(addMemberId);
        MatchingRoom matchingRoom = matchService.findManageMatchingRoom(roomId, requestMember);

        matchingRoom.addMangerRole(addMember);
    }

    @Transactional
    public void removeManager(Long roomId, Long removeMemberId, Member requestMember) {
        Member removeMember = memberServiceApi.findMember(removeMemberId);
        MatchingRoom matchingRoom = matchService.findManageMatchingRoom(roomId, requestMember);

        matchingRoom.removeManagerRole(requestMember, removeMember);
    }

    @Transactional
    public void deactivateMatchingRoom(Long roomId, Member requestMember) {
        MatchingRoom matchingRoom = matchService.findManageMatchingRoom(roomId, requestMember);

        matchingRoom.deactivateMatchingRoom(requestMember);
    }

    @Transactional
    public void customMatching(Long roomId, Member requestMember, CustomGroupingRequest request) {
        List<Member> members = memberServiceApi.findMembersByIds(request.memberIds());
        MatchingRoom matchingRoom = matchService.findManageMatchingRoom(roomId, requestMember);
        List<MatchingRoomMember> matchingRoomMembers = matchingRoom.findMatchingRoomMemberByMembers(members);

        matchingRoom.addGroup(MatchGroup.createMatchGroup(matchingRoom, matchingRoomMembers));
    }

    @Transactional
    public void changeGroupStatus (Long roomId, Member requestMember, GroupStatusChangeRequest request) {
        MatchingRoom matchingRoom = matchService.findManageMatchingRoom(roomId, requestMember);

        matchingRoom.handleGroupStatusChange(request.groupId(), request.status());
    }

}
