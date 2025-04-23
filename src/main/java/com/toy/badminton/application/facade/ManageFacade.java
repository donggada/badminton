package com.toy.badminton.application.facade;

import com.toy.badminton.application.dto.request.ChangeGroupRequest;
import com.toy.badminton.application.dto.request.RoomParticipationRequest;
import com.toy.badminton.application.dto.response.RoomParticipationResponse;
import com.toy.badminton.domain.factory.matching.MatchingFactory;
import com.toy.badminton.domain.factory.matching.MatchingType;
import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.domain.service.MatchGroupService;
import com.toy.badminton.domain.service.MatchingInfoService;
import com.toy.badminton.domain.service.MatchingRoomService;
import com.toy.badminton.domain.service.MemberService;
import com.toy.badminton.infrastructure.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManageFacade {
    private final MatchingRoomService matchingRoomService;
    private final MatchGroupService matchGroupService;
    private final MatchingInfoService matchingInfoService;
    private final MemberService memberService;
    private final MatchingFactory matchingFactory;

    // todo 그룹수정
    public void changeGroup(Long roomId, Member member, ChangeGroupRequest request) {
        MatchingRoom matchingRoom = matchingRoomService.findManageMatchingRoom(roomId, member);
        matchingRoom.validateChangeRequestMembersExist(request);
        Member requestMember = memberService.findMember(request.requesterId());
        Member targetMember = memberService.findMember(request.targetMemberId());
        //todo 수정 내용
        matchGroupService.changeGroups(request.groupId(), requestMember, targetMember);
    }

    @Transactional
    public RoomParticipationResponse participationRoom(Long roomId, Member member, RoomParticipationRequest request) {
        MatchingRoom matchingRoom = matchingRoomService.findManageMatchingRoom(roomId, member);

        return null;
    }

    @Transactional
    public void startMatch(Long roomId, MatchingType type) {
        MatchingRoom matchingRoom = matchingRoomService.findMatchingRoom(roomId);

        List<MatchGroup> matchGroups = matchingFactory.getService(type).startMatching(matchingRoom);
        matchGroupService.savaAllMatchGroup(matchGroups);

        Set<Member> memberSet = matchGroups.stream().flatMap(group -> group.getMembers().stream()).collect(Collectors.toSet());
        matchingInfoService.changeStatusByMatched(matchingRoom.getMatchingInfos(), memberSet);
    }
}
