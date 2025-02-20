package com.toy.badminton.domain.factory.matching;

import com.toy.badminton.domain.model.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BalancedMatchingService implements MatchService {
    @Override
    public List<MatchGroup> startMatching(MatchingRoom matchingRoom) {
        ArrayList<MatchGroup> matchGroupList = new ArrayList<>();

        matchingRoom.validateMinActiveMembers(DOUBLES);
        List<Member> memberList = matchingRoom.getActiveMembers();

        // 1. 멤버들을 능력치나 레벨을 기준으로 정렬 (예시: Level.val 기준)
        memberList.sort(Comparator.comparingInt(Member::getLevelValue));

        // 2. 밸런스를 맞추기 위한 매칭 그룹 생성
        for (int i = 0; i <= memberList.size() - 4; i += 4) {
            List<Member> group = memberList.subList(i, i + 4);

            List<Member> balancedGroup = balanceGroup(group);

            MatchGroup matchGroup = MatchGroup.createMatchGroup(matchingRoom, balancedGroup);
            matchGroupList.add(matchGroup);
        }

        return matchGroupList;
    }


    private List<Member> balanceGroup(List<Member> group) {
        // 그룹 내에서 능력치나 레벨 차이를 최소화 하기 위해 조합을 변경
        List<Member> balancedGroup = new ArrayList<>(group);

        // 예시: 가장 높은 레벨과 가장 낮은 레벨을 섞는 방식 (기타 조건에 맞게 조정 가능)
        balancedGroup.sort(Comparator.comparingInt(Member::getLevelValue));

        // 특정 로직을 통해 그룹을 재조정하여 능력치가 유사한 그룹으로 만들 수 있음
        // 이 예시는 기본적인 수준에서 레벨을 기준으로 조합을 섞는 방법을 보여주고 있음.

        return balancedGroup;
    }
}
