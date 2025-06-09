package com.toy.badminton.application.dto.request.manager;

import com.toy.badminton.domain.factory.matching.MatchingType;

public record StartMatchingRequest(
        MatchingType  type
) {
}
