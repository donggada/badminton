package com.toy.badminton.domain.match;

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
        List<MatchingRoomMember> roomMember = matchingRoom.getActiveRoomMember();

        List<MatchingRoomMember> balanceMemberList = balanceGroup(roomMember);

        for (int i = 0; i <= balanceMemberList.size() - 4; i += 4) {
            List<MatchingRoomMember> group = balanceMemberList.subList(i, i + 4);

            MatchGroup matchGroup = MatchGroup.createMatchGroup(matchingRoom, sort(group));
            matchGroupList.add(matchGroup);
        }

        return matchGroupList;
    }


    private List<MatchingRoomMember> balanceGroup(List<MatchingRoomMember> group) {
        List<MatchingRoomMember> balancedGroup = new ArrayList<>(group);

        balancedGroup.sort(Comparator.comparingInt(MatchingRoomMember::getLevelValue));

        return balancedGroup;
    }
}
