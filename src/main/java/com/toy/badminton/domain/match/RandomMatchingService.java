package com.toy.badminton.domain.match;

import com.toy.badminton.domain.member.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RandomMatchingService implements MatchingService {

    @Override
    public List<MatchGroup> startMatching(MatchingRoom matchingRoom) {
        ArrayList<MatchGroup> matchGroupList = new ArrayList<>();

        matchingRoom.validateMinActiveMembers(DOUBLES);
        List<MatchingRoomMember> memberList = matchingRoom.getMatchingRoomMembers();

        Collections.shuffle(memberList);

        for (int i = 0; i <= memberList.size() - 4; i += 4) {
            List<MatchingRoomMember> group = memberList.subList(i, i + 4);
            MatchGroup matchGroup = MatchGroup.createMatchGroup(matchingRoom, sort(group));
            matchGroupList.add(matchGroup);
        }


        return matchGroupList;
    }
}
