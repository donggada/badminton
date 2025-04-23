package com.toy.badminton.domain.service;

import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoomRepository;
import com.toy.badminton.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.toy.badminton.infrastructure.exception.ErrorCode.INVALID_MATCHING_ROOM;

@Service
@RequiredArgsConstructor
public class MatchingRoomService {

    private final MatchingRoomRepository matchingRoomRepository;


    public MatchingRoom createRoom(String name, Member member) {
        return matchingRoomRepository.save(MatchingRoom.createMatchingRoom(name, member));
    }

    public MatchingRoom findMatchingRoom(Long matchingRoomId) {
        return matchingRoomRepository.findById(matchingRoomId).orElseThrow(() -> INVALID_MATCHING_ROOM.build(matchingRoomId));
    }

    public MatchingRoom findManageMatchingRoom (Long roomId, Member member) {
        MatchingRoom matchingRoom = findMatchingRoom(roomId);
        matchingRoom.validateManager(member);
        return matchingRoom;
    }
}
