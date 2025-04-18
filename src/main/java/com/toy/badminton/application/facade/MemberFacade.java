package com.toy.badminton.application.facade;

import com.toy.badminton.application.dto.response.MemberSignupResponse;
import com.toy.badminton.domain.model.member.Level;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.domain.model.member.MemberRepository;
import com.toy.badminton.domain.service.MemberService;
import com.toy.badminton.application.dto.request.LoginRequest;
import com.toy.badminton.application.dto.request.MemberSignupRequest;
import com.toy.badminton.application.dto.response.LoginResponse;
import com.toy.badminton.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberSignupResponse registerMember (MemberSignupRequest request) {
        memberService.validateNewMember(request);

        test();
        return MemberSignupResponse.of(
                memberService.saveMember(request)
        );
    }

    public void test () {
        Member fixture = Member.builder().build();
        memberRepository.save(fixture);
    }

    public LoginResponse loginMember(LoginRequest request) {
        String loginId = memberService.authenticateMember(request).getLoginId();
        return LoginResponse.of(loginId, jwtTokenProvider.generateToken(loginId));
    }
}
