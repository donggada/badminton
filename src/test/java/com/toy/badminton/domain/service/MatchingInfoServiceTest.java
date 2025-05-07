package com.toy.badminton.domain.service;

import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus;
import com.toy.badminton.domain.model.member.Level;
import com.toy.badminton.domain.model.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
class MatchingInfoServiceTest {
    @InjectMocks
    private MatchingInfoService matchingInfoService;

    @Test
    @DisplayName("updateStatusToMatched - 매칭된 멤버의 상태를 MATCHED로 변경한다")
    void updateStatusToMatched() {
        Member member1 = Member.builder().id(1L).level(Level.GROUP_A).build();
        Member member2 = Member.builder().id(2L).level(Level.MASTER).build();
        Member member3 = Member.builder().id(3L).level(Level.GROUP_C).build();
        Member member4 = Member.builder().id(4L).level(Level.GROUP_B).build();
        Set<Member> set = Set.of(member1, member2, member3);

        MatchingInfo info1 = MatchingInfo.builder().member(member1).status(MatchingStatus.WAITING).build();
        MatchingInfo info2 = MatchingInfo.builder().member(member2).status(MatchingStatus.WAITING).build();
        MatchingInfo info3 = MatchingInfo.builder().member(member3).status(MatchingStatus.WAITING).build();
        MatchingInfo info4 = MatchingInfo.builder().member(member4).status(MatchingStatus.WAITING).build();
        List<MatchingInfo> infos = List.of(info1, info2, info3, info4);

        matchingInfoService.updateStatusToMatched(infos, set);

        assertEquals(MatchingStatus.MATCHED, info1.getStatus());
        assertEquals(MatchingStatus.MATCHED, info2.getStatus());
        assertEquals(MatchingStatus.MATCHED, info3.getStatus());
        assertNotEquals(MatchingStatus.MATCHED, info4.getStatus());
    }


}