package com.toy.badminton.presentation.match.response;

import com.toy.badminton.domain.match.MatchingInfo;
import com.toy.badminton.domain.member.Member;

import java.util.Set;

import static com.toy.badminton.domain.match.MatchingStatus.MATCHED;

public record EnterMember(
        Long memberId,
        String name,
        String level,
        String status,
        boolean isManger
) {

    public static EnterMember of (MatchingInfo matchingInfo, Set<Member> managerList) {
        return new EnterMember(
                matchingInfo.getMember().getId(),
                matchingInfo.getMember().getUsername(),
                matchingInfo.getMember().getLevelDescription(),
                matchingInfo.getStatusDescription(),
                managerList.contains(matchingInfo.getMember()));
    }

    public static EnterMember of (Member member) {
        return new EnterMember(member.getId(), member.getUsername(), member.getLevelDescription(), MATCHED.getDescription(), false);
    }
}
