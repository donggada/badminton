package com.toy.badminton.domain.service.match;

import com.toy.badminton.application.dto.param.ChangeGroupParameters;
import com.toy.badminton.application.dto.request.manager.GroupStatusChangeRequest;
import com.toy.badminton.domain.factory.matching.MatchingFactory;
import com.toy.badminton.domain.factory.matching.MatchingType;
import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoomQuerydslRepository;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoomRepository;
import com.toy.badminton.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.toy.badminton.infrastructure.exception.ErrorCode.INVALID_MATCHING_ROOM;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchingRoomRepository matchingRoomRepository;
    private final MatchingRoomQuerydslRepository matchingRoomQuerydslRepository;
    private final MatchingFactory matchingFactory;

    @Override
    @Transactional
    public void changeMatchingGroupMemberStatus(ChangeGroupParameters parameters, Member targetMember, Member replacementMember) {
        MatchingRoom matchingRoom = findManageMatchingRoom(parameters.roomId(), parameters.member());

        matchingRoom.validateChangeRequestMembersExist(parameters.request());
        matchingRoom.replaceMatchGroupMember(parameters.groupId(), targetMember ,replacementMember);
        matchingRoom.changeMatchingStatus(replacementMember, MatchingStatus.MATCHED);
        matchingRoom.changeMatchingStatus(targetMember, MatchingStatus.WAITING);
    }

    @Override
    @Transactional
    public void participationRoom(Long roomId, Member member, String roomName) {
        MatchingRoom matchingRoom = findManageMatchingRoom(roomId, member);
        matchingRoom.updateRoomName(roomName);
    }

    @Override
    @Transactional
    public void addManagerRole(Long roomId, Member addMember, Member requesterMember) {
        MatchingRoom matchingRoom = findManageMatchingRoom(roomId, requesterMember);
        matchingRoom.addMangerRole(addMember);
    }

    @Override
    @Transactional
    public void removeManagerRole(Long roomId, Member removeMember, Member requesterMember) {
        MatchingRoom matchingRoom = findManageMatchingRoom(roomId, requesterMember);
        matchingRoom.removeManagerRole(requesterMember, removeMember);
    }

    @Override
    @Transactional
    public void deactivateMatchingRoom(Long roomId, Member requesterMember) {
        MatchingRoom matchingRoom = findManageMatchingRoom(roomId, requesterMember);
        matchingRoom.deactivateMatchingRoom(requesterMember);
    }

    @Override
    @Transactional
    public void handleTypeMatching(Long roomId, Member member, MatchingType type) {
        MatchingRoom matchingRoom = findManageMatchingRoom(roomId, member);
        List<MatchGroup> matchGroups = createMatchGroups(matchingRoom, type);
        matchGroups.forEach(matchGroup -> processAddGroup(matchingRoom, matchGroup));
    }

    @Override
    @Transactional
    public void handleCustomMatching(Long roomId, Member requestMember, List<Member> members) {
        MatchingRoom matchingRoom = findManageMatchingRoom(roomId, requestMember);
        MatchGroup addMatchGroup = MatchGroup.createMatchGroup(matchingRoom, members);
        processAddGroup(matchingRoom,addMatchGroup);
    }

    private void processAddGroup(MatchingRoom matchingRoom, MatchGroup addMatchGroup) {
        try {
            matchingRoom.addGroup(addMatchGroup);
        }catch (Exception  e) {
            log.error("processAddGroup error {}, MatchingRoom = {}, MatchGroup = {}", e.getMessage(), matchingRoom, addMatchGroup, e);
        }
    }

    @Override
    @Transactional
    public void changeGroupStatus(Long roomId, Member requestMember, GroupStatusChangeRequest request) {
        MatchingRoom matchingRoom = findManageMatchingRoom(roomId, requestMember);
        matchingRoom.findMembersByGroupId(request.groupId())
                .forEach(member -> matchingRoom.changeMatchingStatus(member, request.status()));

        matchingRoom.endGroupByGroupId(request.groupId());
    }

    private MatchingRoom findManageMatchingRoom(Long matchingRoomId, Member requestMember) {
        MatchingRoom matchingRoom = matchingRoomQuerydslRepository.findRoomWithDetailsById(matchingRoomId)
                .orElseThrow(() -> INVALID_MATCHING_ROOM.build(matchingRoomId));
        matchingRoom.validateRoomActive();
        matchingRoom.validateManager(requestMember);
        return matchingRoom;
    }

    private List<MatchGroup> createMatchGroups(MatchingRoom matchingRoom, MatchingType type) {
        return matchingFactory.getService(type).startMatching(matchingRoom);
    }

}
