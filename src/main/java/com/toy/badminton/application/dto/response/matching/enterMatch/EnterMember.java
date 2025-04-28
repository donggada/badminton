package com.toy.badminton.application.dto.response.matching.enterMatch;

import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus;
import com.toy.badminton.domain.model.member.Member;

public record EnterMember(
        Long memberId,
        String name,
        String level,
        MatchingStatus status
) {

    public static EnterMember of (MatchingInfo matchingInfo) {
        return new EnterMember(matchingInfo.getMember().getId(), matchingInfo.getMember().getUsername(), matchingInfo.getMember().getLevelDescription(), matchingInfo.getStatus());
    }

    public static EnterMember of (Member member) {
        return new EnterMember(member.getId(), member.getUsername(), member.getLevelDescription(), MatchingStatus.MATCHED);
    }
}
