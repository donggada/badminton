package com.toy.badminton.application.controller.match;

import com.toy.badminton.application.dto.request.CreateMatchingRoomRequest;
import com.toy.badminton.application.dto.request.RoomParticipationRequest;
import com.toy.badminton.application.dto.response.CreateMatchingRoomResponse;
import com.toy.badminton.application.dto.response.RoomParticipationResponse;
import com.toy.badminton.application.facade.MatchingFacade;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.infrastructure.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/matching-room")
public class MatchingController {

    private final MatchingFacade matchingFacade;

    @PostMapping()
    public CreateMatchingRoomResponse createRoom(CreateMatchingRoomRequest request, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return matchingFacade.createRoom(request, customUserDetails.getMember());
    }

    @PatchMapping("/{roomId}")
    public RoomParticipationResponse changeMatchingStatus(@PathVariable Long roomId, @AuthenticationPrincipal CustomUserDetails customUserDetails, MatchingStatus status) {
        return matchingFacade.changeMatchingStatus(roomId, customUserDetails.getMember(), status);
    }

}

