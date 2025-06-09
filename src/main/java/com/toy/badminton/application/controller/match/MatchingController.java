package com.toy.badminton.application.controller.match;

import com.toy.badminton.application.dto.request.ChangeMatchingStatusRequest;
import com.toy.badminton.application.dto.request.CreateMatchingRoomRequest;
import com.toy.badminton.application.dto.response.matching.CreateMatchingRoomResponse;
import com.toy.badminton.application.dto.response.matching.MatchingRoomResponse;
import com.toy.badminton.application.dto.response.matching.enterMatch.MatchingRoomDetailResponse;
import com.toy.badminton.application.facade.MatchingFacade;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.infrastructure.security.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/matching-room")
public class MatchingController {

    private final MatchingFacade matchingFacade;

    @GetMapping("")
    public List<MatchingRoomResponse> getMatchingRoomList() {
        //todo Front 2번 호출 수정필요
        return matchingFacade.getMatchingRoomList();
    }

    @GetMapping("/{roomId}")
    public MatchingRoomDetailResponse getMatchingRoomDetail(@PathVariable Long roomId, @AuthMember Member member) {
        return matchingFacade.getMatchingRoomDetail(roomId, member);
    }

    @PostMapping()
    public CreateMatchingRoomResponse createRoom(
            @RequestBody CreateMatchingRoomRequest request,
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

    @PostMapping("entry/{entryCode}")
    public MatchingRoomDetailResponse enterCodeMatchingRoom(
            @PathVariable String entryCode,
            @AuthMember Member member
    ) {
        return matchingFacade.enterCodeMatchingRoom(entryCode, member);
    }

    @PatchMapping("/{roomId}/status")
    public void changeMatchingStatus(
            @PathVariable Long roomId,
            @AuthMember Member member,
            @RequestBody ChangeMatchingStatusRequest request
    ) {
        matchingFacade.changeMatchingStatus(roomId, member, request);
    }

}

