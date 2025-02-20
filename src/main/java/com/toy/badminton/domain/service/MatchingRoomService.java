package com.toy.badminton.domain.service;

import com.toy.badminton.domain.model.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.matchingRoom.MatchingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.toy.badminton.infrastructure.exception.ErrorCode.INVALID_MATCHING_ROOM;

@Service
@RequiredArgsConstructor
public class MatchingRoomService {

    private final MatchingRoomRepository matchingRoomRepository;


    public MatchingRoom createRoom(String name) {
        return matchingRoomRepository.save(MatchingRoom.createMatchingRoom(name));
    }

    public MatchingRoom findMatchingRoom(Long matchingRoomId) {
        return matchingRoomRepository.findById(matchingRoomId).orElseThrow(() -> INVALID_MATCHING_ROOM.build(matchingRoomId));
    }

}
