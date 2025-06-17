package com.toy.badminton.presentation.match.response;

import com.toy.badminton.domain.match.MatchGroup;
import com.toy.badminton.domain.match.MatchingRoom;
import com.toy.badminton.domain.match.MatchingRoomMember;
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
        List<EnterMember> enterMembers = matchingRoom.getMatchingRoomMembers().stream()
                .filter(MatchingRoomMember::isInRoom)
                .map(roomMember -> EnterMember.of(roomMember, matchingRoom.getManagerList()))
                .toList();

        List<Group> groups = matchingRoom.getMatchGroups().stream().filter(MatchGroup::isNotGame).map(Group::of).toList();
        return new MatchingRoomDetailResponse(
                matchingRoom.getId(),
                matchingRoom.getName(),
                matchingRoom.getEntryCode(),
                enterMembers,
                groups,
                matchingRoom.isManager(member)
        );
    }
}
