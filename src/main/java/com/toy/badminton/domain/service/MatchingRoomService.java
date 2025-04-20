package com.toy.badminton.domain.service;

import com.toy.badminton.application.dto.request.ChangeGroupRequest;
import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoomRepository;
import com.toy.badminton.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        matchingRoom.validModifiableMembers(member);
        return matchingRoom;
    }


    @Transactional
    public MatchingRoom changeGroups(MatchingRoom matchingRoom, ChangeGroupRequest request) {
        List<MatchGroup> matchGroups = matchingRoom.getMatchGroups();


        return matchingRoom;
    }

}
