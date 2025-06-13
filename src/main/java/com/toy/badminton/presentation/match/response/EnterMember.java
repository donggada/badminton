package com.toy.badminton.presentation.match.response;

import com.toy.badminton.domain.match.MatchingRoomMember;
import com.toy.badminton.domain.member.Member;

import java.util.Set;

public record EnterMember(
        Long memberId,
        String name,
        String level,
        String status,
        boolean isManger
) {

    public static EnterMember of (MatchingRoomMember matchingRoomMember, Set<Member> managerList) {
        return new EnterMember(
                matchingRoomMember.getMember().getId(),
                matchingRoomMember.getMember().getUsername(),
                matchingRoomMember.getMember().getLevelDescription(),
                matchingRoomMember.getStatusDescription(),
                managerList.contains(matchingRoomMember.getMember()));
    }

    public static EnterMember of (MatchingRoomMember matchingRoomMember) {
        Member member = matchingRoomMember.getMember();
        return new EnterMember(member.getId(), member.getUsername(), member.getLevelDescription(), matchingRoomMember.getStatusDescription(), false);
    }
}
