package com.toy.badminton.presentation.match.request;

public record ChangeGroupRequest(
        Long replacementMemberId,
        Long targetMemberId
) {
}
