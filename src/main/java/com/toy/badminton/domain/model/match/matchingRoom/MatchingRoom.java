package com.toy.badminton.domain.model.match.matchingRoom;

import com.toy.badminton.domain.model.BaseTimeEntity;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.toy.badminton.infrastructure.exception.ErrorCode.MATCHING_ROOM_EDIT_FORBIDDEN;
import static com.toy.badminton.infrastructure.exception.ErrorCode.NOT_ENOUGH_MATCHING_MEMBERS;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class MatchingRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "matchingRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingInfo> matchingInfos = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "matchingRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchGroup> matchGroups = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "matching_room_manager_members",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<Member> managerList = new HashSet<>();

    private MatchingRoom(String name) {
        this.name = name;
        this.managerList = new HashSet<>();
    }

    public List<Member> getActiveMembers() {
        return matchingInfos.stream()
                .filter(MatchingInfo::isWaiting)
                .map(MatchingInfo::getMember)
                .collect(Collectors.toList());
    }

    public void validateMinActiveMembers(int minSize) {
        long activeSize = matchingInfos.stream()
                .filter(MatchingInfo::isWaiting)
                .count();

        if (activeSize < minSize) {
            throw NOT_ENOUGH_MATCHING_MEMBERS.build(minSize, activeSize);
        }
    }

    public void validModifiableMembers(Member member) {
        if (!managerList.contains(member)) {
            throw MATCHING_ROOM_EDIT_FORBIDDEN.build(member.getId());
        }
    }

    public static MatchingRoom createMatchingRoom(String name, Member member) {
        MatchingRoom matchingRoom = new MatchingRoom(name);
        matchingRoom.managerList.add(member);
        return matchingRoom;
    }
}
