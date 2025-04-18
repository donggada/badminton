package com.toy.badminton.domain.factory.matching;

import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RandomMatchingService implements MatchService {

    @Override
    public List<MatchGroup> startMatching(MatchingRoom matchingRoom) {
        ArrayList<MatchGroup> matchGroupList = new ArrayList<>();

        matchingRoom.validateMinActiveMembers(DOUBLES);
        List<Member> memberList = matchingRoom.getActiveMembers();

        Collections.shuffle(memberList);

        for (int i = 0; i <= memberList.size() - 4; i += 4) {
            List<Member> group = memberList.subList(i, i + 4);
            MatchGroup matchGroup = MatchGroup.createMatchGroup(matchingRoom, sort(group));
            matchGroupList.add(matchGroup);
        }


        return matchGroupList;
    }
}
