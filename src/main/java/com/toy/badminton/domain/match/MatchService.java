package com.toy.badminton.domain.match;

import com.toy.badminton.presentation.match.vo.ChangeGroupParameters;
import com.toy.badminton.presentation.match.request.GroupStatusChangeRequest;
import com.toy.badminton.infrastructure.jpa.persistence.match.MatchingRoomQuerydslRepository;
import com.toy.badminton.domain.member.Member;
import com.toy.badminton.infrastructure.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.toy.badminton.infrastructure.exception.ErrorCode.INVALID_ENTRY_CODE;
import static com.toy.badminton.infrastructure.exception.ErrorCode.INVALID_MATCHING_ROOM;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchingRoomRepository matchingRoomRepository;
    private final MatchingRoomQuerydslRepository matchingRoomQuerydslRepository;
    private final MatchingFactory matchingFactory;

    public MatchingRoom createRoom(String name, Member requestMember) {
        validateDailyRoomCreation(requestMember);
        return matchingRoomRepository.save(MatchingRoom.createMatchingRoom(name, requestMember));
    }

    @Transactional(readOnly = true)
    public MatchingRoom findActiveRoom(Long roomId) {
        return findAndValidateActiveRoom(roomId);
    }

    @Transactional(readOnly = true)
    public List<MatchingRoom> findMatchingRoomList() {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        return matchingRoomQuerydslRepository.findMatchingRoomsWithActiveMembers(startOfDay, endOfDay);
    }

    public MatchingRoom findAndValidateActiveRoom(Long roomId) {
        MatchingRoom matchingRoom = matchingRoomQuerydslRepository.findRoomWithDetailsById(roomId)
                .orElseThrow(() -> INVALID_MATCHING_ROOM.build(roomId));
        matchingRoom.validateRoomActive();
        return matchingRoom;
    }

    public MatchingRoom findActiveRoomByEntryCode(String entryCode) {
        MatchingRoom matchingRoom = matchingRoomQuerydslRepository.findRoomWithDetailsByEntryCode(entryCode)
                .orElseThrow(() -> INVALID_ENTRY_CODE.build(entryCode));
        matchingRoom.validateRoomActive();
        return matchingRoom;
    }

    public MatchingRoom findManageMatchingRoom(Long matchingRoomId, Member requestMember) {
        MatchingRoom matchingRoom = matchingRoomQuerydslRepository.findRoomWithDetailsById(matchingRoomId)
                .orElseThrow(() -> INVALID_MATCHING_ROOM.build(matchingRoomId));
        matchingRoom.validateRoomActive();
        matchingRoom.validateManager(requestMember);
        return matchingRoom;
    }

    public List<MatchGroup> createMatchGroups(MatchingRoom matchingRoom, MatchingType type) {
        return matchingFactory.getService(type).startMatching(matchingRoom);
    }

    private void validateDailyRoomCreation(Member requestMember) {
        matchingRoomQuerydslRepository.findTodayCreatedRoomByMemberId(requestMember.getId())
                .ifPresent(room -> {
                    throw ErrorCode.DAILY_ROOM_CREATION_LIMIT.build();
                });
    }

}
