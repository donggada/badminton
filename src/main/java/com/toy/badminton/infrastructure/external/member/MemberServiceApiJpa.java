package com.toy.badminton.infrastructure.external.member;

import com.toy.badminton.api.member.MemberServiceApi;
import com.toy.badminton.domain.member.Member;
import com.toy.badminton.domain.member.MemberCustomRepository;
import com.toy.badminton.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.toy.badminton.infrastructure.exception.ErrorCode.INVALID_MEMBER;

@Service
@RequiredArgsConstructor
public class MemberServiceApiJpa implements MemberServiceApi {

    // 모노놀식 -> DB 조회
    private final MemberRepository memberRepository;
    private final MemberCustomRepository memberCustomRepository;

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> INVALID_MEMBER.build(memberId));
    }

    @Override
    public List<Member> findMembersByIds(List<Long> memberIds) {
        return memberCustomRepository.findMembersByIds(memberIds);
    }

}
