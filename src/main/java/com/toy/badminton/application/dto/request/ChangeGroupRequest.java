package com.toy.badminton.application.dto.request;

public record ChangeGroupRequest(
        Long groupId,
        Long replacementMemberId,
        Long targetMemberId
) {
}
