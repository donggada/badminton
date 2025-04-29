package com.toy.badminton.domain.model.match.matchGroup;

import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.infrastructure.exception.ApplicationException;
import com.toy.badminton.infrastructure.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchGroupTest {

    @Test
    @DisplayName("목표 멤버가 존재하면 교체가 잘 되는지 확인")
    void testReplaceMember_targetMemberExists() {
        Member targetMember = Member.builder().id(1L).build();
        Member replacementMember = Member.builder().id(2L).build();

        List<Member> members = new ArrayList<>();
        members.add(Member.builder().id(1L).build());
        members.add(Member.builder().id(3L).build());
        members.add(Member.builder().id(4L).build());
        members.add(Member.builder().id(5L).build());

        MatchGroup matchGroup = MatchGroup.builder().members(members).build();

        matchGroup.replaceMember(targetMember, replacementMember);

        assertTrue(matchGroup.getMembers().contains(replacementMember));
        assertFalse(matchGroup.getMembers().contains(targetMember));
    }

    @Test
    @DisplayName("목표 멤버가 존재하지 않으면 TARGET_NOT_FOUND 예외가 발생하는지 확인")
    void testReplaceMember_targetMemberNotFound() {
        Member targetMember = Member.builder().id(1L).build();
        Member replacementMember = Member.builder().id(2L).build();

        List<Member> members = new ArrayList<>();
        members.add(Member.builder().id(1L).build());
        members.add(Member.builder().id(3L).build());
        members.add(Member.builder().id(4L).build());
        members.add(Member.builder().id(5L).build());

        MatchGroup matchGroup = MatchGroup.builder().members(members).build();

        matchGroup.replaceMember(targetMember, replacementMember);

        ApplicationException exception = assertThrows(
                ApplicationException.class, () -> matchGroup.replaceMember(targetMember, replacementMember)
        );

        assertEquals(ErrorCode.TARGET_NOT_FOUND.build(targetMember.getId()).getMessage(), exception.getMessage());
    }
}