package com.toy.badminton.application.controller.match;

import com.toy.badminton.application.dto.request.CreateMatchingRoomRequest;
import com.toy.badminton.application.dto.response.matching.CreateMatchingRoomResponse;
import com.toy.badminton.application.dto.response.matching.MatchingRoomResponse;
import com.toy.badminton.application.dto.response.matching.RoomParticipationResponse;
import com.toy.badminton.application.dto.response.matching.enterMatch.MatchingRoomDetailResponse;
import com.toy.badminton.application.facade.MatchingFacade;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.infrastructure.security.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/matching-room")
public class MatchingController {

    private final MatchingFacade matchingFacade;

    @GetMapping("")
    public List<MatchingRoomResponse> getMatchingRoomList() {
        return matchingFacade.getMatchingRoomList();
    }

    @GetMapping("/{roomId}")
    public MatchingRoomDetailResponse getMatchingRoomDetail(@PathVariable Long roomId, @AuthMember Member member) {
        return matchingFacade.getMatchingRoomDetail(roomId, member);
    }

    @PostMapping()
    public CreateMatchingRoomResponse createRoom(
            CreateMatchingRoomRequest request,
            @AuthMember Member member
    ) {
        return matchingFacade.createRoom(request, member);
    }

    @PostMapping("/{roomId}")
    public MatchingRoomDetailResponse enterMatchingRoom(
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

