package com.toy.badminton.application.dto.response.matching.enterMatch;

import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;

import java.util.List;

public record MatchingRoomDetailResponse(
        Long id,
        String name,
        String entryCode,
        List<EnterMember> enterMebmerList,
        List<Group> groupList,
        boolean isManager
) {
    public static MatchingRoomDetailResponse of (MatchingRoom matchingRoom, Member member) {
        return new MatchingRoomDetailResponse(
                matchingRoom.getId(),
                matchingRoom.getName(),
                matchingRoom.getEntryCode(),
                matchingRoom.getMatchingInfos().stream().map(EnterMember::of).toList(),
                matchingRoom.getMatchGroups().stream().map(Group::of).toList(),
                matchingRoom.isManager(member)
        );
    }
}
