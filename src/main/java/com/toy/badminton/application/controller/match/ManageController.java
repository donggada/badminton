package com.toy.badminton.application.controller.match;

import com.toy.badminton.application.dto.param.ChangeGroupParameters;
import com.toy.badminton.application.dto.request.manager.ChangeGroupRequest;
import com.toy.badminton.application.dto.request.RoomParticipationRequest;
import com.toy.badminton.application.dto.request.manager.CustomGroupingRequest;
import com.toy.badminton.application.dto.request.manager.StartMatchingRequest;
import com.toy.badminton.application.facade.ManageFacade;
import com.toy.badminton.domain.factory.matching.MatchingType;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.infrastructure.security.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/manager")
public class ManageController {

    private final ManageFacade manageFacade;

    @PatchMapping("{roomId}/groups/{groupId}/members")
    public ResponseEntity<Void> replaceMatchGroupMember(
            @PathVariable Long roomId,
            @PathVariable Long groupId,
            @AuthMember Member member,
            @RequestBody @Valid ChangeGroupRequest request
    ) {
        ChangeGroupParameters parameters = ChangeGroupParameters.builder()
                .roomId(roomId)
                .groupId(groupId)
                .member(member)
                .request(request)
                .build();
        manageFacade.replaceMatchGroupMember(parameters);
        return noContent();
    }

    @PutMapping("{roomId}/room")
    public ResponseEntity<Void> participationRoom(
            @PathVariable Long roomId,
            @AuthMember Member member,
            @RequestBody RoomParticipationRequest request
    ) {
        manageFacade.participationRoom(roomId, member, request);
        return noContent();
    }

    @PatchMapping("/rooms/{roomId}/managers/{memberId}")
    public ResponseEntity<Void> addManger(
            @PathVariable Long roomId,
            @PathVariable Long memberId,
            @AuthMember Member member
    ) {
        manageFacade.addManager(roomId, memberId, member);
        return noContent();
    }

    @DeleteMapping("/rooms/{roomId}/managers/{memberId}")
    public ResponseEntity<Void> removeManger(
            @PathVariable Long roomId,
            @PathVariable Long memberId,
            @AuthMember Member member
    ) {
        manageFacade.removeManager(roomId, memberId, member);
        return noContent();
    }

    @PatchMapping("/rooms/{roomId}/deactivate")
    public void deactivateMatchingRoom(
            @PathVariable Long roomId,
            @AuthMember Member member
    ) {
        manageFacade.deactivateMatchingRoom(roomId, member);
    }

    @PostMapping("/{roomId}/start")
    public ResponseEntity<Void> startMatching(
            @PathVariable Long roomId,
            @RequestBody StartMatchingRequest request,
            @AuthMember Member member
    ) {
        manageFacade.startMatch(roomId, member, request.type());
        return noContent();
    }

    @PostMapping("/{roomId}/start/custom")
    public ResponseEntity<Void> startCustomMatching(
            @PathVariable Long roomId,
            @RequestBody @Valid CustomGroupingRequest request,
            @AuthMember Member member
    ) {
        manageFacade.customMatching(roomId, member, request);
        return noContent();
    }


    private ResponseEntity<Void> noContent() {
        return ResponseEntity.noContent().build();
    }
}
