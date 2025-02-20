package com.toy.badminton.domain.service;

import com.toy.badminton.domain.model.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.matchGroup.MatchGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchGroupService {

    private final MatchGroupRepository matchGroupRepository;

    public List<MatchGroup> savaAllMatchGroup(List<MatchGroup> matchGroupList) {
        return matchGroupRepository.saveAll(matchGroupList);
    }
}
