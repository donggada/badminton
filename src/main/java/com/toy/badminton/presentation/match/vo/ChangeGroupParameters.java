package com.toy.badminton.presentation.match.vo;

import com.toy.badminton.presentation.match.request.ChangeGroupRequest;
import com.toy.badminton.domain.member.Member;
import lombok.Builder;

@Builder
public record ChangeGroupParameters(
        Long roomId,
        Long groupId,
        Member member,
        ChangeGroupRequest request,
        Member targetMember,
        Member replacementMember
) {

    public Long replacementMemberId() {
        return request.replacementMemberId();
    }

    public Long targetMemberId() {
        return request.targetMemberId();
    }
}
