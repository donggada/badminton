package com.toy.badminton.domain.model.match.matchGroup;

import com.toy.badminton.domain.model.BaseTimeEntity;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

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
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<Member> members = new ArrayList<>();

    public void replaceMember(Member targetMember, Member replacementMember) {
        int index = members.indexOf(targetMember);
        if (index == -1) {
            throw TARGET_NOT_FOUND.build(targetMember.getId());
        }
        members.set(index, replacementMember);
    }

    public void endGame() {
        this.isGameOver = true;
    }

    private MatchGroup(MatchingRoom matchingRoom, List<Member> members) {
        this.matchingRoom = matchingRoom;
        this.members = members;
    }

    public static MatchGroup createMatchGroup(MatchingRoom matchingRoom, List<Member> members) {
        return new MatchGroup(matchingRoom, members);
    }

}

