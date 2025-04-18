package com.toy.badminton.domain.factory.matching;

import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public interface MatchService {
    List<MatchGroup> startMatching(MatchingRoom matchingRoom);
//    int SINGLES = 2;
    int DOUBLES = 4;
    default List<Member> sort (List<Member> memberList) {
        //4명일 경우 사용가능
        List<Member> sortList = new ArrayList<>();
        memberList.sort(Comparator.comparingInt(Member::getLevelValue));

        for (int i = 0; i < 2 ; i++) {
            sortList.add(memberList.get(i));
            sortList.add(memberList.get(memberList.size() - i - 1));
        }

        return sortList;
    }
}
