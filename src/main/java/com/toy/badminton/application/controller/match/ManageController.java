package com.toy.badminton.application.controller.match;

import com.toy.badminton.application.dto.request.ChangeGroupRequest;
import com.toy.badminton.application.dto.request.RoomParticipationRequest;
import com.toy.badminton.application.facade.ManageFacade;
import com.toy.badminton.domain.factory.matching.MatchingType;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.infrastructure.security.CurrentMember;
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

    @PatchMapping("{roomId}/groups/members")
    public ResponseEntity<Void> replaceMatchGroupMember(
            @PathVariable Long roomId,
            @CurrentMember Member member,
            @RequestBody ChangeGroupRequest request
    ) {
        manageFacade.replaceMatchGroupMember(roomId, member, request);
        return noContent();
    }

    @PutMapping("{roomId}/room")
    public ResponseEntity<Void> participationRoom(
            @PathVariable Long roomId,
            @CurrentMember Member member,
            @RequestBody RoomParticipationRequest request
    ) {
        manageFacade.participationRoom(roomId, member, request);
        return noContent();
    }

    @PostMapping("/{roomId}/start")
    public ResponseEntity<Void> startMatching(
            @PathVariable Long roomId,
            @CurrentMember Member member
    ) {
        manageFacade.startMatch(roomId, member, MatchingType.RANDOM);
        return noContent();
    }

    private ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }
}
