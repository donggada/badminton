package com.toy.badminton.infrastructure.security;



import com.toy.badminton.domain.member.Member;
import com.toy.badminton.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.toy.badminton.infrastructure.exception.ErrorCode.MEMBER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) {
        Member member = memberRepository.findTokenCheckByLoginId(loginId)
                .orElseThrow(() -> MEMBER_NOT_FOUND.build(loginId));
        member.validateNotDeleted();
        return new CustomUserDetails(member);
    }
}

