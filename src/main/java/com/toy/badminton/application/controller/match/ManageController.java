package com.toy.badminton.application.controller.match;

import com.toy.badminton.application.dto.request.ChangeGroupRequest;
import com.toy.badminton.application.dto.request.RoomParticipationRequest;
import com.toy.badminton.application.dto.response.RoomParticipationResponse;
import com.toy.badminton.application.facade.ManageFacade;
import com.toy.badminton.domain.factory.matching.MatchingType;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.infrastructure.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/manager")
public class ManageController {

    private final ManageFacade manageFacade;

    @PatchMapping("{roomId}/groups")
    public ResponseEntity<Void> changeGroup(@PathVariable Long roomId, @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ChangeGroupRequest request) {
        manageFacade.changeGroup(roomId, customUserDetails.getMember(), request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{roomId}/room")
    public ResponseEntity<Void> participationRoom(@PathVariable Long roomId, @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody RoomParticipationRequest request) {
        Member member = customUserDetails.getMember();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{roomId}/start")
    public ResponseEntity<Void> startMatching(@PathVariable Long roomId, @AuthenticationPrincipal Member member) {
        manageFacade.startMatch(roomId, MatchingType.RANDOM);
        return ResponseEntity.noContent().build();
    }
}
