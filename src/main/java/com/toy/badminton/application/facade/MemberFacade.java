package com.toy.badminton.application.facade;

import com.toy.badminton.application.dto.request.LoginRequest;
import com.toy.badminton.application.dto.request.MemberSignupRequest;
import com.toy.badminton.application.dto.request.member.DeleteMemberRequest;
import com.toy.badminton.application.dto.request.member.UpdateMemberProfileRequest;
import com.toy.badminton.application.dto.request.member.UpdatePasswordRequest;
import com.toy.badminton.application.dto.response.LoginResponse;
import com.toy.badminton.application.dto.response.MemberSignupResponse;
import com.toy.badminton.application.dto.response.member.MemberProfileResponse;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.domain.service.MemberService;
import com.toy.badminton.infrastructure.exception.ErrorCode;
import com.toy.badminton.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberSignupResponse registerMember (MemberSignupRequest request) {
        memberService.validateNewMember(request);
        return MemberSignupResponse.of(memberService.saveMember(request));
    }

    public LoginResponse loginMember(LoginRequest request) {
        String loginId = memberService.authenticateMember(request).getLoginId();
        return LoginResponse.of(loginId, jwtTokenProvider.generateToken(loginId));
    }

    public MemberProfileResponse getMemberProfile(Member member) {
        return MemberProfileResponse.of(member);
    }


    public MemberProfileResponse updateMemberProfile(UpdateMemberProfileRequest request, Member member) {
        return MemberProfileResponse.of(memberService.updateMemberProfile(request, member));
    }

    public void updatePassword(UpdatePasswordRequest request, Member member) {
        memberService.updatePassword(request, member);
    }

    @Transactional
    public void deleteMember(DeleteMemberRequest request, Member member) {
        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw ErrorCode.INVALID_PASSWORD.build();
        }
        memberService.deleteMember(member);
    }
}
