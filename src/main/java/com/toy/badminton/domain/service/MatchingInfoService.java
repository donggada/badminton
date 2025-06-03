package com.toy.badminton.domain.service;

import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfoRepository;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.toy.badminton.infrastructure.exception.ErrorCode.INVALID_MATCHING_ROOM_INFO;

@Service
@RequiredArgsConstructor
public class MatchingInfoService {

    private final MatchingInfoRepository matchingInfoRepository;

    @Transactional
    public MatchingInfo changeMatchingStatus(MatchingRoom matchingRoom, Member member, MatchingStatus status) {
        MatchingInfo matchingInfo = matchingInfoRepository.findByMatchingRoomAndMember(matchingRoom, member)
                .orElseThrow(() -> INVALID_MATCHING_ROOM_INFO.build(matchingRoom.getId(), member.getId()));
        matchingInfo.changeStatus(status);
        return matchingInfo;
    }


    public void updateStatusToMatched(List<MatchingInfo> matchingInfos, Set<Member> meberSet) {
        matchingInfos.stream()
                .filter(info -> meberSet.contains(info.getMember()))
                .forEach(info -> info.changeStatus(MatchingStatus.MATCHED));

    }
}
