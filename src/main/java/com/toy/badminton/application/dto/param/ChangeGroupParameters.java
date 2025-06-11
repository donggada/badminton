package com.toy.badminton.application.dto.param;

import com.toy.badminton.application.dto.request.manager.ChangeGroupRequest;
import com.toy.badminton.domain.model.member.Member;
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
