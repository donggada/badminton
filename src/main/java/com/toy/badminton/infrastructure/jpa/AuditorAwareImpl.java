package com.toy.badminton.infrastructure.jpa;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        //todo 나중에 변경 시큐리티 적용후 다시 변경 예정
        return Optional.of(1L);
    }
}
