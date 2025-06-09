package com.toy.badminton.application.dto.request.manager;

public record ChangeGroupRequest(
        Long replacementMemberId,
        Long targetMemberId
) {
}
