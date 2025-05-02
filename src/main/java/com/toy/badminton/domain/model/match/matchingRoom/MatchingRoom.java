package com.toy.badminton.domain.model.match.matchingRoom;

import com.toy.badminton.application.dto.request.ChangeGroupRequest;
import com.toy.badminton.application.dto.request.RoomParticipationRequest;
import com.toy.badminton.domain.model.BaseTimeEntity;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.match.matchGroup.MatchGroup;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.infrastructure.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.toy.badminton.infrastructure.exception.ErrorCode.*;

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
    @BatchSize(size = 50)
    @OneToMany(mappedBy = "matchingRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingInfo> matchingInfos = new ArrayList<>();

    @Builder.Default
    @BatchSize(size = 50)
    @OneToMany(mappedBy = "matchingRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchGroup> matchGroups = new ArrayList<>();

    @Builder.Default
    @BatchSize(size = 50)
    @ManyToMany(fetch = FetchType.LAZY)
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

    public void validateManager(Member member) {
        if (!managerList.contains(member)) {
            throw MATCHING_ROOM_EDIT_FORBIDDEN.build(member.getId());
        }
    }

    public void validateChangeRequestMembersExist(ChangeGroupRequest request) {
        validateMemberExists(request.replacementMemberId(), ErrorCode.REQUESTER_NOT_FOUND);
        validateMemberExists(request.targetMemberId(), ErrorCode.TARGET_NOT_FOUND);
    }

    private void validateMemberExists(Long memberId, ErrorCode code) {
        matchingInfos.stream()
                .filter(info -> info.hasMemberId(memberId))
                .findAny()
                .orElseThrow(() -> code.build(memberId));
    }

    public void validateMemberNotExists(Member member) {
        matchingInfos.stream()
                .filter(info -> info.hasMemberId(member.getId()))
                .findAny()
                .ifPresent(info -> { throw DUPLICATE_ENTER.build(member.getId()); });
    }

    public void updateRoomInfo(String roomName, Set<Member> memberSet) {
        name = roomName;
        managerList.addAll(memberSet);
    }

    public static MatchingRoom createMatchingRoom(String name, Member member) {
        MatchingRoom matchingRoom = new MatchingRoom(name);
        matchingRoom.managerList.add(member);
        return matchingRoom;
    }
}
