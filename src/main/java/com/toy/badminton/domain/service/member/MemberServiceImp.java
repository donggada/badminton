package com.toy.badminton.domain.service.member;

import com.toy.badminton.application.dto.request.member.UpdateMemberProfileRequest;
import com.toy.badminton.application.dto.request.member.UpdatePasswordRequest;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.domain.model.member.MemberQuerydslRepository;
import com.toy.badminton.domain.model.member.MemberRepository;
import com.toy.badminton.application.dto.request.LoginRequest;
import com.toy.badminton.application.dto.request.MemberSignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.toy.badminton.infrastructure.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberServiceImp implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberQuerydslRepository memberQuerydslRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Member findMember(Long memberId) {
        return findByMemberId(memberId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> findMembersByIds(List<Long> memberIds) {
        return memberQuerydslRepository.findMembersByIds(memberIds);
    }

    @Override
    public Member saveMember(MemberSignupRequest request) {
        return memberRepository.save(Member.createMember(request, passwordEncoder.encode(request.password())));
    }

    @Override
    @Transactional
    public void updatePassword(UpdatePasswordRequest request, Member member) {
        findByMemberId(member.getId()).updatePassword(passwordEncoder.encode(request.newPassword()));
    }

    @Override
    @Transactional
    public Member updateMemberProfile(UpdateMemberProfileRequest request, Member member) {
        Member findMember = findMember(member.getId());
        findMember.updateProfile(request);
        return findMember;
    }

    @Override
    public Member authenticateMember(LoginRequest request) {
        Member member = memberRepository.findByLoginId(request.loginId())
                .orElseThrow(() -> INVALID_LOGIN_ID.build(request.loginId()));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw INVALID_PASSWORD.build();
        }

        member.validateNotDeleted();

        return member;
    }

    @Override
    public void validateNewMember(MemberSignupRequest request) {
        if (memberRepository.existsByLoginId(request.loginId())) {
            throw DUPLICATE_LOGIN_ID.build(request.loginId());
        }
    }

    @Override
    @Transactional
    public void deleteMember(Member member) {
        Member findMember = findMember(member.getId());
        findMember.markAsDeleted();
    }

    private Member findByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> INVALID_MEMBER.build(memberId));
    }
}
