package com.toy.badminton.domain.member;

import com.toy.badminton.domain.match.MatchingRoomMember;
import com.toy.badminton.presentation.member.request.MemberSignupRequest;
import com.toy.badminton.presentation.member.request.UpdateMemberProfileRequest;
import com.toy.badminton.domain.BaseTimeEntity;
import com.toy.badminton.infrastructure.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, exclude = "matchingRoomMembers")
@ToString
@Builder
@BatchSize(size = 100)
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

    private String profileImage;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingRoomMember> matchingRoomMembers = new ArrayList<>();

    @Builder.Default
    @Column(name = "deleted")
    private boolean isDeleted = false;

    public String getLevelDescription() {
        return level.getDescription();
    }

    public int getLevelValue() {
        return level.getValue();
    }

    private Member(String loginId, String password, String username, String phoneNumber, Level level) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.level = level;
        this.isDeleted = false;
    }

    public static Member createMember(MemberSignupRequest request, String encodePassword) {
        return new Member(request.loginId(), encodePassword, request.username(), request.phoneNumber(), request.level());
    }

    public void updateProfile(UpdateMemberProfileRequest request) {
        this.username = request.name();
        this.profileImage = request.profileImage();
        this.level = request.level();
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }

    public void validateNotDeleted() {
        if (isDeleted) {
            throw ErrorCode.WITHDRAWN_MEMBER.build(id);
        }
    }
}
