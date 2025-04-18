package com.toy.badminton.domain.service;

import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfoRepository;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.toy.badminton.infrastructure.exception.ErrorCode.INVALID_MATCHING_ROOM_INFO;

@Service
@RequiredArgsConstructor
public class MatchingInfoService {

    private final MatchingInfoRepository matchingInfoRepository;

    public MatchingInfo joinRoom(MatchingRoom matchingRoom, Member member) {
        return matchingInfoRepository.save(MatchingInfo.createMatchingInfo(matchingRoom, member));
    }

    @Transactional
    public MatchingInfo leaveRoom(MatchingRoom matchingRoom, Member member) {
        MatchingInfo matchingInfo = matchingInfoRepository.findByMatchingRoomAndMember(matchingRoom, member)
                .orElseThrow(() -> INVALID_MATCHING_ROOM_INFO.build(matchingRoom.getId(), member.getId()));

        matchingInfo.leaveRoom();
        return matchingInfo;
    }

}
