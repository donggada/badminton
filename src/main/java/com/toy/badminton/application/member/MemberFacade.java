package com.toy.badminton.application.member;

import com.toy.badminton.domain.member.Member;
import com.toy.badminton.domain.member.MemberService;
import com.toy.badminton.infrastructure.exception.ErrorCode;
import com.toy.badminton.infrastructure.security.JwtTokenProvider;
import com.toy.badminton.presentation.member.request.*;
import com.toy.badminton.presentation.member.response.LoginResponse;
import com.toy.badminton.presentation.member.response.MemberProfileResponse;
import com.toy.badminton.presentation.member.response.MemberSignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.toy.badminton.infrastructure.exception.ErrorCode.INVALID_PASSWORD;

@Service
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberSignupResponse registerMember (MemberSignupRequest request) {
        return MemberSignupResponse.of(memberService.saveMember(request, passwordEncoder.encode(request.password())));
    }

    public LoginResponse loginMember(LoginRequest request) {
        String loginId = request.loginId();
        Member member = memberService.findByLoginId(loginId);

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw INVALID_PASSWORD.build();
        }

        member.validateNotDeleted();

        return LoginResponse.of(loginId, jwtTokenProvider.generateToken(loginId));
    }

    public MemberProfileResponse getMemberProfile(Member member) {
        return MemberProfileResponse.of(member);
    }


    @Transactional
    public MemberProfileResponse updateMemberProfile(UpdateMemberProfileRequest request, Member member) {
        Member findMember = memberService.findMember(member.getId());
        findMember.updateProfile(request);
        return MemberProfileResponse.of(findMember);
    }

    @Transactional
    public void updatePassword(UpdatePasswordRequest request, Member member) {
        Member findMember = memberService.findMember(member.getId());
        findMember.updatePassword(passwordEncoder.encode(request.newPassword()));
    }

    @Transactional
    public void deleteMember(DeleteMemberRequest request, Member member) {
        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw ErrorCode.INVALID_PASSWORD.build();
        }

        Member findMember = memberService.findMember(member.getId());
        findMember.markAsDeleted();
    }
}
