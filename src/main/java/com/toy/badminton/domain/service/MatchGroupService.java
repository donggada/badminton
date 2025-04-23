package com.toy.badminton.domain.service;

import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.match.matchGroup.MatchGroupRepository;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchGroupService {

    private final MatchGroupRepository matchGroupRepository;

    public List<MatchGroup> savaAllMatchGroup(List<MatchGroup> matchGroupList) {
        return matchGroupRepository.saveAll(matchGroupList);
    }

    @Transactional
    public void changeGroups(Long matchGroupId, Member requestMember, Member targetMember) {
        MatchGroup group = matchGroupRepository.findById(matchGroupId).orElseThrow(() -> new RuntimeException(""));

        for (Member m : group.getMembers()) {
            if (m.equals(targetMember)) {
                m = requestMember;
            }
        }
    }
}
