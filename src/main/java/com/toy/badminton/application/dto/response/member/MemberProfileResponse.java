package com.toy.badminton.application.dto.response.member;

import com.toy.badminton.domain.model.member.Member;

import java.time.LocalDateTime;

public record MemberProfileResponse(
        Long id,
        String name,
        String profileImage,
        String level,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static MemberProfileResponse of(Member member) {
        return new MemberProfileResponse(
                member.getId(),
                member.getUsername(),
                member.getProfileImage(),
                member.getLevelDescription(),
                member.getCreatedDate(),
                member.getUpdatedDate()
        );
    }
} 