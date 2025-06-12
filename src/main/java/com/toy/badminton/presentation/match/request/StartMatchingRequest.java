package com.toy.badminton.presentation.match.request;

import com.toy.badminton.domain.match.MatchingType;

public record StartMatchingRequest(
        MatchingType  type
) {
}
