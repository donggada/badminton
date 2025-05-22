package com.toy.badminton.domain.service;

import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoomQuerydslRepository;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoomRepository;
import com.toy.badminton.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.toy.badminton.infrastructure.exception.ErrorCode.INVALID_MATCHING_ROOM;

@Service
@RequiredArgsConstructor
public class MatchingRoomService {

    private final MatchingRoomRepository matchingRoomRepository;
    private final MatchingRoomQuerydslRepository matchingRoomQuerydslRepository;


    public MatchingRoom createRoom(String name, Member member) {
        return matchingRoomRepository.save(MatchingRoom.createMatchingRoom(name, member));
    }

    public MatchingRoom findMatchingRoom(Long matchingRoomId) {
        return matchingRoomQuerydslRepository.findWithAllById(matchingRoomId)
                .orElseThrow(() -> INVALID_MATCHING_ROOM.build(matchingRoomId));
    }

    public MatchingRoom findManageMatchingRoom (Long roomId, Member member) {
        MatchingRoom matchingRoom = findMatchingRoom(roomId);
        matchingRoom.validateManager(member);
        return matchingRoom;
    }

    public List<MatchingRoom> findMatchingRoomList() {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        return matchingRoomQuerydslRepository.findMatchingRoomsWithActiveMembers(startOfDay, endOfDay);
    }
}
