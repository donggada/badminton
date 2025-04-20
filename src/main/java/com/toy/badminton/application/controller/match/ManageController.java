package com.toy.badminton.application.controller.match;

import com.toy.badminton.application.dto.request.ChangeGroupRequest;
import com.toy.badminton.application.dto.request.RoomParticipationRequest;
import com.toy.badminton.application.dto.response.RoomParticipationResponse;
import com.toy.badminton.application.facade.ManageFacade;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.infrastructure.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/manager")
public class ManageController {

    private final ManageFacade manageFacade;

    @PatchMapping("{roomId}/groups")
    public void changeGroup(@PathVariable Long roomId, @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ChangeGroupRequest request) {
        manageFacade.changeGroup(roomId, customUserDetails.getMember(), request);
    }

    @PutMapping("{roomId}/room")
    public RoomParticipationResponse participationRoom(@PathVariable Long roomId, @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody RoomParticipationRequest request) {
        Member member = customUserDetails.getMember();

        return null;
    }
}
