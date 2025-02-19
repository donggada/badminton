package com.toy.badminton.domain.model.matchingInfo;

import com.toy.badminton.domain.model.BaseTimeEntity;
import com.toy.badminton.domain.model.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private boolean isActive;

    private MatchingInfo(MatchingRoom matchingRoom, Member member) {
        this.matchingRoom = matchingRoom;
        this.member = member;
        this.isActive = true;
    }

    public static MatchingInfo createMatchingInfo(MatchingRoom matchingRoom, Member member) {
        return new MatchingInfo(matchingRoom, member);
    }

    public void leaveRoom() {
        this.isActive = false;
    }

    public String getMatchingRoomName() {
        return matchingRoom.getName();
    }

    public String getMemberName() {
        return member.getUsername();
    }

    public String getMessage() {
        return isActive ? "입장" : "퇴장";
    }
}
