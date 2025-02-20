package com.toy.badminton.domain.factory.matching;

import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.toy.badminton.infrastructure.exception.ErrorCode.INVALID_MATCHING_SERVICE;

@Service
public class MatchingFactory {

    private final Map<Class, MatchService> matchServiceMap;

    public MatchingFactory(List<MatchService> matchServiceList) {
        this.matchServiceMap = matchServiceList.stream().collect(
                Collectors.toMap(
                        AopUtils::getTargetClass,
                        service ->service
                )
        );
    }

    public MatchService getService(MatchingType type) {
        return switch (type) {
            case RANDOM -> matchServiceMap.get(RandomMatchingService.class);
            case BALANCED -> matchServiceMap.get(BalancedMatchingService.class);
            default -> throw INVALID_MATCHING_SERVICE.build(type);
        };
    }
}
