package com.toy.badminton.application.facade;

import com.toy.badminton.application.dto.response.MemberSignupResponse;
import com.toy.badminton.domain.service.MemberService;
import com.toy.badminton.application.dto.request.LoginRequest;
import com.toy.badminton.application.dto.request.MemberSignupRequest;
import com.toy.badminton.application.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberService memberService;

    public MemberSignupResponse registerMember (MemberSignupRequest request) {
        return MemberSignupResponse.of(
                memberService.saveMember(request)
        );
    }

    public LoginResponse loginMember(LoginRequest request) {
        return LoginResponse.of(
                memberService.authenticateMember(request)
        );
    }
}
