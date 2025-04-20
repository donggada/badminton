package com.toy.badminton.application.facade;

import com.toy.badminton.application.dto.request.ChangeGroupRequest;
import com.toy.badminton.application.dto.request.RoomParticipationRequest;
import com.toy.badminton.application.dto.response.RoomParticipationResponse;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.domain.service.MatchingInfoService;
import com.toy.badminton.domain.service.MatchingRoomService;
import com.toy.badminton.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManageFacade {
    private final MatchingRoomService matchingRoomService;
    private final MatchingInfoService matchingInfoService;
    private final MemberService memberService;

    // todo 그룹수정
    public MatchingRoom changeGroup(Long roomId, Member member, ChangeGroupRequest request) {
        MatchingRoom matchingRoom = matchingRoomService.findManageMatchingRoom(roomId, member);
        List<MatchingInfo> matchingInfos = matchingRoom.getMatchingInfos();
        matchingInfos.stream()
                .filter(MatchingInfo::isWaiting)
                .filter(matchingInfo -> matchingInfo.getId().equals(request.requesterId())).toList();


        //todo 수정 내용
        return matchingRoomService.changeGroups(matchingRoom, request);
    }

    @Transactional
    public RoomParticipationResponse participationRoom(Long roomId, Member member, RoomParticipationRequest request) {
        MatchingRoom matchingRoom = matchingRoomService.findManageMatchingRoom(roomId, member);

        return null;
    }



}
