package com.toy.badminton.application.controller;

import com.toy.badminton.application.dto.request.CreateMatchingRoomRequest;
import com.toy.badminton.application.dto.request.RoomParticipationRequest;
import com.toy.badminton.application.dto.response.CreateMatchingRoomResponse;
import com.toy.badminton.application.dto.response.RoomParticipationResponse;
import com.toy.badminton.application.facade.MatchingFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    @GetMapping("/test")
    public List<WaitingPlayer> test () {

        Random random = new Random();
        int n = random.nextInt(10);
        ArrayList<WaitingPlayer> result = new ArrayList<>();
        for (int i = 1; i <= n ; i++) {
            result.add(new WaitingPlayer(i, "지원자" + i, i + "분"));
        }

        return result;
    }

    record WaitingPlayer(
            int id,
            String name,
            String time
    ) {

    }

}

