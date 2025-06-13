package com.toy.badminton.domain.match;

import com.toy.badminton.domain.member.Member;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public interface MatchingService {
    List<MatchGroup> startMatching(MatchingRoom matchingRoom);
//    int SINGLES = 2;
    int DOUBLES = 4;
    default List<MatchingRoomMember> sort (List<MatchingRoomMember> memberList) {
        //4명일 경우 사용가능
        List<MatchingRoomMember> sortList = new ArrayList<>();
        memberList.sort(Comparator.comparingInt(MatchingRoomMember::getLevelValue));

        for (int i = 0; i < 2 ; i++) {
            sortList.add(memberList.get(i));
            sortList.add(memberList.get(memberList.size() - i - 1));
        }

        return sortList;
    }
}
