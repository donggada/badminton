package com.toy.badminton.application.controller;

import com.toy.badminton.application.dto.request.CreateMatchingRoomRequest;
import com.toy.badminton.application.dto.request.RoomParticipationRequest;
import com.toy.badminton.application.dto.response.CreateMatchingRoomResponse;
import com.toy.badminton.application.dto.response.RoomParticipationResponse;
import com.toy.badminton.application.facade.MatchingFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/matching-room")
public class MatchingController {

    private final MatchingFacade matchingFacade;

    @PostMapping()
    public CreateMatchingRoomResponse createRoom(CreateMatchingRoomRequest request) {
        return matchingFacade.createRoom(request);
    }

    @PostMapping("/{roomId}/participants")
    public RoomParticipationResponse joinRoom(@PathVariable Long roomId, @RequestBody RoomParticipationRequest request) {
        //todo 시큐리티 변경후 memberID 변경
        return matchingFacade.joinRoom(roomId,request);
    }

    @PatchMapping ("/{roomId}/participants/{memberId}")
    public RoomParticipationResponse leaveRoom(@PathVariable Long roomId, @RequestBody @PathVariable Long memberId) {
        //todo 시큐리티 변경후 memberID 변경
        return matchingFacade.leaveRoom(roomId, memberId);
    }
}
