package com.toy.badminton.domain.model.member;

import com.toy.badminton.application.dto.request.MemberSignupRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Level level;

    private Member(String loginId, String password, String username, Level level) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.level = level;
    }

    public static Member createMember(MemberSignupRequest request) {
        return new Member(request.loginId(), request.password(), request.username(), request.level());
    }

    public String  getLevelDescription() {
        return level.getDescription();
    }
}
