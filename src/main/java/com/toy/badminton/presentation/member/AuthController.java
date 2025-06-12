package com.toy.badminton.presentation.member;


import com.toy.badminton.presentation.member.request.LoginRequest;
import com.toy.badminton.presentation.member.response.LoginResponse;
import com.toy.badminton.application.member.MemberFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final MemberFacade memberFacade;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return memberFacade.loginMember(request);
    }

}
