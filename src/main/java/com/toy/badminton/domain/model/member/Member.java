package com.toy.badminton.domain.model.member;

import com.toy.badminton.application.dto.request.MemberSignupRequest;
import com.toy.badminton.domain.model.BaseTimeEntity;
import com.toy.badminton.domain.model.matchingInfo.MatchingInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingInfo> matchingInfos = new ArrayList<>();

    public Member(String loginId, String password, String username, String phoneNumber, Level level) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.level = level;
    }

    public static Member createMember(MemberSignupRequest request) {
        return new Member(request.loginId(), request.password(), request.username(), request.phoneNumber(), request.level());
    }

    public String  getLevelDescription() {
        return level.getDescription();
    }
}
