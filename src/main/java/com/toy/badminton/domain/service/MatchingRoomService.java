package com.toy.badminton.domain.service;

import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoomQuerydslRepository;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoomRepository;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.infrastructure.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.toy.badminton.infrastructure.exception.ErrorCode.INVALID_ENTRY_CODE;
import static com.toy.badminton.infrastructure.exception.ErrorCode.INVALID_MATCHING_ROOM;

@Service
@RequiredArgsConstructor
public class MatchingRoomService {

    private final MatchingRoomRepository matchingRoomRepository;
    private final MatchingRoomQuerydslRepository matchingRoomQuerydslRepository;

    @Transactional
    public MatchingRoom createRoom(String name, Member member) {
        validateDailyRoomCreation(member);
        return matchingRoomRepository.save(MatchingRoom.createMatchingRoom(name, member));
    }


    public MatchingRoom findActiveRoom(Long matchingRoomId) {
        return findAndValidateActiveRoom(matchingRoomId);
    }


    public MatchingRoom findManageMatchingRoom(Long roomId, Member member) {
        MatchingRoom matchingRoom = findAndValidateActiveRoom(roomId);
        matchingRoom.validateManager(member);
        return matchingRoom;
    }

    private MatchingRoom findAndValidateActiveRoom(Long matchingRoomId) {
        MatchingRoom matchingRoom = matchingRoomQuerydslRepository.findRoomWithDetailsById(matchingRoomId)
                .orElseThrow(() -> INVALID_MATCHING_ROOM.build(matchingRoomId));
        matchingRoom.validateRoomActive();
        return matchingRoom;
    }

    public MatchingRoom findActiveRoomByEntryCode(String entryCode) {
        MatchingRoom matchingRoom = matchingRoomQuerydslRepository.findRoomWithDetailsByEntryCode(entryCode)
                .orElseThrow(() -> INVALID_ENTRY_CODE.build(entryCode));
        matchingRoom.validateRoomActive();
        return matchingRoom;
    }

    public List<MatchingRoom> findMatchingRoomList() {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        return matchingRoomQuerydslRepository.findMatchingRoomsWithActiveMembers(startOfDay, endOfDay);
    }

    private void validateDailyRoomCreation(Member member) {
        matchingRoomQuerydslRepository.findTodayCreatedRoomByMemberId(member.getId())
                .ifPresent(room -> {
            throw ErrorCode.DAILY_ROOM_CREATION_LIMIT.build();
        });
    }
}
