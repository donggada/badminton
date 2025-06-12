package com.toy.badminton.domain.match;

import com.toy.badminton.domain.member.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
public class BalancedMatchingService implements MatchingService {

    @Override
    public List<MatchGroup> startMatching(MatchingRoom matchingRoom) {
        ArrayList<MatchGroup> matchGroupList = new ArrayList<>();

        matchingRoom.validateMinActiveMembers(DOUBLES);
        List<Member> memberList = matchingRoom.getActiveMembers();

        List<Member> balanceMemberList = balanceGroup(memberList);

        for (int i = 0; i <= balanceMemberList.size() - 4; i += 4) {
            List<Member> group = balanceMemberList.subList(i, i + 4);

            MatchGroup matchGroup = MatchGroup.createMatchGroup(matchingRoom, sort(group));
            matchGroupList.add(matchGroup);
        }

        return matchGroupList;
    }

    private List<Member> balanceGroup(List<Member> group) {
        List<Member> balancedGroup = new ArrayList<>(group);

        balancedGroup.sort(Comparator.comparingInt(Member::getLevelValue));

        return balancedGroup;
    }
}
