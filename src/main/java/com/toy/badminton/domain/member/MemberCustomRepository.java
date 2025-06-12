package com.toy.badminton.domain.member;

import java.util.List;

public interface MemberCustomRepository {
    List<Member> findMembersByIds (List<Long> memberIds);
}
