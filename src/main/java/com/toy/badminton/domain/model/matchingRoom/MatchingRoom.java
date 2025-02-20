package com.toy.badminton.domain.model.matchingRoom;

import com.toy.badminton.domain.model.BaseTimeEntity;
import com.toy.badminton.domain.model.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.toy.badminton.infrastructure.exception.ErrorCode.NOT_ENOUGH_MATCHING_MEMBERS;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@ToString
public class MatchingRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "matchingRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingInfo> matchingInfos = new ArrayList<>();

    @OneToMany(mappedBy = "matchingRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchGroup> matchGroups = new ArrayList<>();

    private MatchingRoom(String name) {
        this.name = name;
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

    public static MatchingRoom createMatchingRoom(String name) {
        return new MatchingRoom(name);
    }

    public static MatchingRoom fixture(Long id, String name, List<MatchingInfo> matchingInfos, List<MatchGroup> matchGroups) {
        return new MatchingRoom(
                id,
                name,
                matchingInfos,
                matchGroups
        );
    }
}
