package com.toy.badminton.application.facade;

import com.toy.badminton.application.dto.request.CreateMatchingRoomRequest;
import com.toy.badminton.application.dto.request.RoomParticipationRequest;
import com.toy.badminton.application.dto.response.CreateMatchingRoomResponse;
import com.toy.badminton.application.dto.response.RoomParticipationResponse;
import com.toy.badminton.domain.factory.matching.MatchService;
import com.toy.badminton.domain.factory.matching.MatchingFactory;
import com.toy.badminton.domain.factory.matching.MatchingType;
import com.toy.badminton.domain.model.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.service.MatchGroupService;
import com.toy.badminton.domain.service.MatchingInfoService;
import com.toy.badminton.domain.service.MatchingRoomService;
import com.toy.badminton.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingFacade {
    private final MemberService memberService;
    private final MatchingRoomService matchingRoomService;
    private final MatchingInfoService matchingInfoService;
    private final MatchGroupService matchGroupService;
    private final MatchingFactory matchingFactory;

    public CreateMatchingRoomResponse createRoom(CreateMatchingRoomRequest request) {
        return CreateMatchingRoomResponse.of(
                matchingRoomService.createRoom(request.roomName())
        );
    }

    public RoomParticipationResponse joinRoom(Long matchingRoomId, RoomParticipationRequest request) {
        return RoomParticipationResponse.of(
                matchingInfoService.joinRoom(
                        matchingRoomService.findMatchingRoom(matchingRoomId),
                        memberService.findMember(request.MemberId())
                )
        );
    }

    public RoomParticipationResponse leaveRoom(Long matchingRoomId, Long memberId) {
        return RoomParticipationResponse.of(
                matchingInfoService.leaveRoom(
                        matchingRoomService.findMatchingRoom(matchingRoomId),
                        memberService.findMember(memberId))
        );
    }

    public String startMatch(Long roomId, MatchingType type) {
        MatchingRoom matchingRoom = matchingRoomService.findMatchingRoom(roomId);

        List<MatchGroup> matchGroups = matchingFactory.getService(type).startMatching(matchingRoom);
        matchGroupService.savaAllMatchGroup(matchGroups);
        return "";
    }
}
