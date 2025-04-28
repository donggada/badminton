package com.toy.badminton.application.controller.match;

import com.toy.badminton.application.dto.request.CreateMatchingRoomRequest;
import com.toy.badminton.application.dto.response.matching.CreateMatchingRoomResponse;
import com.toy.badminton.application.dto.response.matching.RoomParticipationResponse;
import com.toy.badminton.application.dto.response.matching.enterMatch.MatchingRoomResponse;
import com.toy.badminton.application.facade.MatchingFacade;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.infrastructure.security.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/matching-room")
public class MatchingController {

    private final MatchingFacade matchingFacade;

    @GetMapping("/{roomId}")
    public MatchingRoomResponse getMatchingRoomDetail(@PathVariable Long roomId) {
        return matchingFacade.getMatchingRoomDetail(roomId);
    }

    @PostMapping()
    public CreateMatchingRoomResponse createRoom(
            CreateMatchingRoomRequest request,
            @AuthMember Member member
    ) {
        return matchingFacade.createRoom(request, member);
    }

    @PostMapping("/{roomId}")
    public MatchingRoomResponse enterMatchingRoom(
            @PathVariable Long roomId,
            @AuthMember Member member
    ) {
        return matchingFacade.enterMatchingRoom(roomId, member);
    }

    @PatchMapping("/{roomId}")
    public RoomParticipationResponse changeMatchingStatus(
            @PathVariable Long roomId,
            @AuthMember Member member,
            MatchingStatus status
    ) {
        return matchingFacade.changeMatchingStatus(roomId, member, status);
    }

}

