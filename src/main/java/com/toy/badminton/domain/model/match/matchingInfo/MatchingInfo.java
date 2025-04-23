package com.toy.badminton.domain.model.match.matchingInfo;

import com.toy.badminton.domain.model.BaseTimeEntity;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus.LEFT_ROOM;
import static com.toy.badminton.domain.model.match.matchingInfo.MatchingStatus.WAITING;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class MatchingInfo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_room_id")
    private MatchingRoom matchingRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private MatchingStatus status;

    private LocalDateTime leaveDate;

    public void changeStatus(MatchingStatus status) {
        this.status = status;
        if (status.equals(LEFT_ROOM)) {
            this.leaveDate = LocalDateTime.now();
        }
    }

    public boolean isWaiting() {
        return status.equals(WAITING);
    }

    public String getMatchingRoomName() {
        return matchingRoom.getName();
    }

    public String getMemberName() {
        return member.getUsername();
    }

    public boolean hasMemberId(Long memberId) {
        return Objects.equals(member.getId(), memberId);
    }

    public String getMessage() {
        return status.getDescription();
    }

    public static MatchingInfo createMatchingInfo(MatchingRoom matchingRoom, Member member) {
        return new MatchingInfo(matchingRoom, member);
    }

    private MatchingInfo(MatchingRoom matchingRoom, Member member) {
        this.matchingRoom = matchingRoom;
        this.member = member;
        this.status = WAITING;
    }
}
