package com.toy.badminton.application.facade;

import com.toy.badminton.application.dto.request.CreateMatchingRoomRequest;
import com.toy.badminton.application.dto.response.CreateMatchingRoomResponse;
import com.toy.badminton.application.dto.response.RoomParticipationResponse;
import com.toy.badminton.application.dto.response.enterMatch.EnterMatchingResponse;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingFacade {

    private final MatchingRoomService matchingRoomService;
    private final MatchingInfoService matchingInfoService;

    public CreateMatchingRoomResponse createRoom(CreateMatchingRoomRequest request, Member member) {

        return CreateMatchingRoomResponse.of(
                matchingRoomService.createRoom(request.roomName(), member)
        );
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
    public EnterMatchingResponse enterMatchingRoom(Long roomId, Member member) {
        MatchingRoom matchingRoom = matchingRoomService.findMatchingRoom(roomId);
        matchingRoom.validateMemberNotExists(member);
        matchingRoom.getMatchingInfos().add(matchingInfoService.saveMatchingInfo(matchingRoom, member));
        return EnterMatchingResponse.of(matchingRoom);
    }
}
