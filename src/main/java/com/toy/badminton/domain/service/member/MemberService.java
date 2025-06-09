package com.toy.badminton.domain.service.member;

import com.toy.badminton.application.dto.request.LoginRequest;
import com.toy.badminton.application.dto.request.MemberSignupRequest;
import com.toy.badminton.application.dto.request.member.UpdateMemberProfileRequest;
import com.toy.badminton.application.dto.request.member.UpdatePasswordRequest;
import com.toy.badminton.domain.model.member.Member;

import java.util.List;

public interface MemberService {

    Member findMember(Long memberId);

    Member saveMember(MemberSignupRequest request);

    void updatePassword(UpdatePasswordRequest request, Member member);

    Member updateMemberProfile(UpdateMemberProfileRequest request, Member member);

    Member authenticateMember(LoginRequest request);

    void validateNewMember(MemberSignupRequest request);

    void deleteMember(Member member);

    List<Member> findMembersByIds(List<Long> memberIds);

}
