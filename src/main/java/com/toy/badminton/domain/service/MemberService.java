package com.toy.badminton.domain.service;

import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.domain.model.member.MemberRepository;
import com.toy.badminton.application.dto.request.LoginRequest;
import com.toy.badminton.application.dto.request.MemberSignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.toy.badminton.infrastructure.exception.ErrorCode.INVALID_LOGIN_ID;
import static com.toy.badminton.infrastructure.exception.ErrorCode.INVALID_MEMBER;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member saveMember(MemberSignupRequest request) {
        return memberRepository.save(Member.createMember(request));
    }

    public Member authenticateMember(LoginRequest request) {
        return memberRepository.findByLoginIdAndPassword(request.login(), request.password())
                .orElseThrow(() -> INVALID_LOGIN_ID.build(request.login()));
    }

    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> INVALID_MEMBER.build(memberId));
    }
}
