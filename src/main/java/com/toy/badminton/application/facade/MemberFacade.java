package com.toy.badminton.application.facade;

import com.toy.badminton.application.dto.request.LoginRequest;
import com.toy.badminton.application.dto.request.MemberSignupRequest;
import com.toy.badminton.application.dto.response.LoginResponse;
import com.toy.badminton.application.dto.response.MemberSignupResponse;
import com.toy.badminton.domain.service.MemberService;
import com.toy.badminton.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public MemberSignupResponse registerMember (MemberSignupRequest request) {
        memberService.validateNewMember(request);
        return MemberSignupResponse.of(memberService.saveMember(request));
    }

    public LoginResponse loginMember(LoginRequest request) {
        String loginId = memberService.authenticateMember(request).getLoginId();
        return LoginResponse.of(loginId, jwtTokenProvider.generateToken(loginId));
    }
}
