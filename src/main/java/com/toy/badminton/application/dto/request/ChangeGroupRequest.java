package com.toy.badminton.application.dto.request;

import com.toy.badminton.domain.model.member.Member;

import java.util.Set;

public record ChangeGroupRequest(
        Long groupId,
        Long requesterId,
        Long targetMemberId
) {
}
