package com.toy.badminton.domain.match;

import com.toy.badminton.domain.BaseTimeEntity;
import com.toy.badminton.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class MatchingRoomMember extends BaseTimeEntity {
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
    @Enumerated(EnumType.STRING)
    private MatchingStatus status;

    private LocalDateTime leaveDate;

    public void changeStatus(MatchingStatus status) {
        this.status = status;
        if (status.equals(MatchingStatus.LEFT_ROOM)) {
            this.leaveDate = LocalDateTime.now();
        }
    }

    public boolean isWaiting() {
        return status == MatchingStatus.WAITING;
    }

    public boolean isInRoom() {
        return status != MatchingStatus.LEFT_ROOM;
    }

    public String getMatchingRoomName() {
        return matchingRoom.getName();
    }

    public String getMemberName() {
        return member.getUsername();
    }

    public int getLevelValue() {
        return member.getLevelValue();
    }

    public boolean isMember(Member member) {
        return this.member.equals(member);
    }

    public String getStatusDescription() {
        return status.getDescription();
    }


    public static MatchingRoomMember createMatchingInfo(MatchingRoom matchingRoom, Member member) {
        return new MatchingRoomMember(matchingRoom, member);
    }

    private MatchingRoomMember(MatchingRoom matchingRoom, Member member) {
        this.matchingRoom = matchingRoom;
        this.member = member;
        this.status = MatchingStatus.WAITING;
    }
}
