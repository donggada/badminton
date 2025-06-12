package com.toy.badminton.presentation.match;

import com.toy.badminton.presentation.match.request.ChangeMatchingStatusRequest;
import com.toy.badminton.presentation.match.request.CreateMatchingRoomRequest;
import com.toy.badminton.presentation.match.response.CreateMatchingRoomResponse;
import com.toy.badminton.presentation.match.response.MatchingRoomResponse;
import com.toy.badminton.presentation.match.response.MatchingRoomDetailResponse;
import com.toy.badminton.application.match.MatchingFacade;
import com.toy.badminton.domain.member.Member;
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

