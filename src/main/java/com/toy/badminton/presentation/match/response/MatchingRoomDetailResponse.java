package com.toy.badminton.presentation.match.response;

import com.toy.badminton.domain.match.MatchingRoom;
import com.toy.badminton.domain.member.Member;

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
                matchingRoom.getMatchingInfos().stream().map(info -> EnterMember.of(info, matchingRoom.getManagerList())).toList(),
                matchingRoom.getMatchGroups().stream().map(Group::of).toList(),
                matchingRoom.isManager(member)
        );
    }
}
