package com.toy.badminton.api.member;

import com.toy.badminton.domain.member.Member;

import java.util.List;

public interface MemberServiceApi {
    Member findMember(Long memberId);
    List<Member> findMembersByIds (List<Long> memberIds);
}
