package com.toy.badminton.domain.match;

import com.toy.badminton.domain.BaseTimeEntity;
import com.toy.badminton.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.toy.badminton.infrastructure.exception.ErrorCode.MEMBER_ALREADY_IN_GROUP;
import static com.toy.badminton.infrastructure.exception.ErrorCode.TARGET_NOT_FOUND;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class MatchGroup extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private boolean isGameOver = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_room_id")
    private MatchingRoom matchingRoom;

    @Builder.Default
    @BatchSize(size = 50)
    @ManyToMany
    @JoinTable(
            name = "match_group_members",
            joinColumns = @JoinColumn(name = "match_group_id"),
            inverseJoinColumns = @JoinColumn(name = "matching_room_member_id")
    )
    private List<MatchingRoomMember> matchingRoomMembers = new ArrayList<>();

    public void replaceMember(MatchingRoomMember target, MatchingRoomMember replacement) {
        int index = matchingRoomMembers.indexOf(target);
        if (index == -1) {
            throw TARGET_NOT_FOUND.build(target.getId());
        }

        if (matchingRoomMembers.contains(replacement)) {
            throw MEMBER_ALREADY_IN_GROUP.build(id, replacement.getId());
        }

        matchingRoomMembers.set(index, replacement);
    }

    public List<Member> getMember () {
        return matchingRoomMembers.stream().map(MatchingRoomMember::getMember).toList();
    }

    public void endGame() {
        this.isGameOver = true;
    }

    public boolean isNotGame() {
        return !isGameOver;
    }

    public boolean isSameId(Long groupId) {
        return Objects.equals(id, groupId);
    }

    private MatchGroup(MatchingRoom matchingRoom, List<MatchingRoomMember> matchingRoomMembers) {
        this.matchingRoom = matchingRoom;
        this.matchingRoomMembers = matchingRoomMembers;
    }


    public static MatchGroup createMatchGroup(MatchingRoom matchingRoom, List<MatchingRoomMember> members) {
        return new MatchGroup(matchingRoom, members);
    }

}

