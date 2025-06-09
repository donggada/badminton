package com.toy.badminton.domain.service;

import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus;
import com.toy.badminton.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MatchingInfoService {

    public void updateStatusToMatched(List<MatchingInfo> matchingInfos, Set<Member> meberSet) {
        matchingInfos.stream()
                .filter(info -> meberSet.contains(info.getMember()))
                .forEach(info -> info.changeStatus(MatchingStatus.MATCHED));

    }
}
