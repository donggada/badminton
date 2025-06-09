package com.toy.badminton.domain.service;

import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.match.matchGroup.MatchGroupRepository;
import com.toy.badminton.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.toy.badminton.infrastructure.exception.ErrorCode.INVALID_MATCHING_GROUP;

@Service
@RequiredArgsConstructor
public class MatchGroupService {

    private final MatchGroupRepository matchGroupRepository;

    public List<MatchGroup> savaAllMatchGroup(List<MatchGroup> matchGroupList) {
        return matchGroupRepository.saveAll(matchGroupList);
    }

    public void replaceMatchGroupMember(Long matchGroupId, Member targetMember, Member replacementMember) {
        matchGroupRepository.findById(matchGroupId)
                .orElseThrow(() -> INVALID_MATCHING_GROUP.build(matchGroupId))
                .replaceMember(targetMember, replacementMember);
    }
}
