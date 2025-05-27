package com.toy.badminton.application.controller.member;

import com.toy.badminton.application.dto.request.MemberSignupRequest;
import com.toy.badminton.application.dto.request.member.DeleteMemberRequest;
import com.toy.badminton.application.dto.request.member.UpdateMemberProfileRequest;
import com.toy.badminton.application.dto.request.member.UpdatePasswordRequest;
import com.toy.badminton.application.dto.response.MemberSignupResponse;
import com.toy.badminton.application.dto.response.member.MemberProfileResponse;
import com.toy.badminton.application.facade.MemberFacade;
import com.toy.badminton.domain.model.member.Member;
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
