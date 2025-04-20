package com.toy.badminton.application.controller.member;

import com.toy.badminton.application.dto.request.MemberSignupRequest;
import com.toy.badminton.application.dto.response.MemberSignupResponse;
import com.toy.badminton.application.facade.MemberFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member")
public class MemberController {
    private final MemberFacade memberFacade;

    @PostMapping("")
    public MemberSignupResponse signupMember (@Valid @RequestBody MemberSignupRequest request) {
        return memberFacade.registerMember(request);
    }







}
