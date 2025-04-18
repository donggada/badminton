package com.toy.badminton.domain.model.match.matchGroup;

import com.toy.badminton.domain.model.BaseTimeEntity;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_room_id")
    private MatchingRoom matchingRoom;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "match_group_members",
            joinColumns = @JoinColumn(name = "match_group_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<Member> members = new ArrayList<>();

    private MatchGroup(MatchingRoom matchingRoom, List<Member> members) {
        this.matchingRoom = matchingRoom;
        this.members = members;
    }

    public static MatchGroup createMatchGroup(MatchingRoom matchingRoom, List<Member> members) {
        return new MatchGroup(matchingRoom, members);
    }

}

