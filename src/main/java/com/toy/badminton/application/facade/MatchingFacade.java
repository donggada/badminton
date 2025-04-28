package com.toy.badminton.application.facade;

import com.toy.badminton.application.dto.request.CreateMatchingRoomRequest;
import com.toy.badminton.application.dto.response.matching.CreateMatchingRoomResponse;
import com.toy.badminton.application.dto.response.matching.RoomParticipationResponse;
import com.toy.badminton.application.dto.response.matching.enterMatch.MatchingRoomResponse;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.domain.service.MatchingInfoService;
import com.toy.badminton.domain.service.MatchingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchingFacade {

    private final MatchingRoomService matchingRoomService;
    private final MatchingInfoService matchingInfoService;

    public CreateMatchingRoomResponse createRoom(CreateMatchingRoomRequest request, Member member) {
        return CreateMatchingRoomResponse.of(matchingRoomService.createRoom(request.roomName(), member));
    }

    public RoomParticipationResponse changeMatchingStatus (Long matchingRoomId, Member member, MatchingStatus status) {
        return RoomParticipationResponse.of(
                matchingInfoService.changeMatchingStatus(
                        matchingRoomService.findMatchingRoom(matchingRoomId),
                        member,
                        status
                )
        );
    }

    @Transactional
    public MatchingRoomResponse enterMatchingRoom(Long roomId, Member member) {
        MatchingRoom matchingRoom = matchingRoomService.findMatchingRoom(roomId);
        matchingRoom.validateMemberNotExists(member);
        matchingRoom.getMatchingInfos().add(matchingInfoService.saveMatchingInfo(matchingRoom, member));
        return MatchingRoomResponse.of(matchingRoom);
    }

    public MatchingRoomResponse getMatchingRoomDetail(Long roomId) {
        return MatchingRoomResponse.of(matchingRoomService.findMatchingRoom(roomId));
    }
}
