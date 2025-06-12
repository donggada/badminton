package com.toy.badminton.presentation.member;

import com.toy.badminton.presentation.member.request.MemberSignupRequest;
import com.toy.badminton.presentation.member.request.DeleteMemberRequest;
import com.toy.badminton.presentation.member.request.UpdateMemberProfileRequest;
import com.toy.badminton.presentation.member.request.UpdatePasswordRequest;
import com.toy.badminton.presentation.member.response.MemberSignupResponse;
import com.toy.badminton.presentation.member.response.MemberProfileResponse;
import com.toy.badminton.application.member.MemberFacade;
import com.toy.badminton.domain.member.Member;
import com.toy.badminton.infrastructure.security.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member")
public class MemberController {

    private final MemberFacade memberFacade;

    @PostMapping("")
    public MemberSignupResponse signupMember (@Valid @RequestBody MemberSignupRequest request) {
        return memberFacade.registerMember(request);
    }

    @GetMapping
    public MemberProfileResponse getMemberProfile(@AuthMember Member member) {
        return memberFacade.getMemberProfile(member);
    }

    @PutMapping
    public MemberProfileResponse updateMemberProfile(
            @Valid @RequestBody UpdateMemberProfileRequest request,
            @AuthMember Member member
    ) {
        return memberFacade.updateMemberProfile(request, member);
    }

    @PutMapping("/password")
    public void updatePassword(
            @Valid @RequestBody UpdatePasswordRequest request,
            @AuthMember Member member
    ) {
        memberFacade.updatePassword(request, member);
    }

    @DeleteMapping
    public void deleteMember(
            @Valid @RequestBody DeleteMemberRequest request,
            @AuthMember Member member
    ) {
        memberFacade.deleteMember(request, member);
    }
}
