package com.toy.badminton.application.controller;


import com.toy.badminton.application.dto.request.LoginRequest;
import com.toy.badminton.application.dto.response.LoginResponse;
import com.toy.badminton.application.facade.MemberFacade;
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
