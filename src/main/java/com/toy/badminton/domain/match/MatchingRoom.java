package com.toy.badminton.domain.match;

import com.toy.badminton.presentation.match.request.ChangeGroupRequest;
import com.toy.badminton.domain.BaseTimeEntity;
import com.toy.badminton.domain.member.Member;
import com.toy.badminton.infrastructure.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.toy.badminton.domain.match.MatchingService.DOUBLES;
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

    @Column(unique = true)
    private String entryCode;

    @Builder.Default
    private boolean isActive = true;

    @Builder.Default
    @BatchSize(size = 50)
    @OneToMany(mappedBy = "matchingRoom", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<MatchingInfo> matchingInfos = new ArrayList<>();

    @Builder.Default
    @BatchSize(size = 50)
    @OneToMany(mappedBy = "matchingRoom", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<MatchGroup> matchGroups = new ArrayList<>();

    @Builder.Default
    @BatchSize(size = 50)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    public void addMember(Member member, MatchingInfo matchingInfo) {
        Optional<MatchingInfo> existingInfo = findMatchingInfoByMember(member);

        if (existingInfo.isPresent()) {
            existingInfo.get().changeStatus(MatchingStatus.WAITING);
            return;
        }

        matchingInfos.add(matchingInfo);
    }
    public void addGroup(MatchGroup matchGroup) {
        validateAddGroupMemberCount(matchGroup);
        validateAddGroupMemberAllInRoom(matchGroup);
        matchGroups.add(matchGroup);
        matchGroup.getMembers().forEach(member -> changeMatchingStatus(member, MatchingStatus.MATCHED));
    }

    private void validateAddGroupMemberAllInRoom(MatchGroup matchGroup) {
        matchGroup.getMembers().forEach(this::validateMemberInRoom);
    }

    private void validateAddGroupMemberCount(MatchGroup matchGroup) {
        int addCount = new HashSet<>(matchGroup.getMembers()).size();

        if (addCount != DOUBLES) {
            throw NOT_ENOUGH_MATCHING_MEMBERS.build(DOUBLES, addCount);
        }
    }

    public void addMangerRole(Member targetMember) {
        validateMemberInRoom(targetMember);
        managerList.add(targetMember);
    }

    public void removeManagerRole(Member requesterMember, Member targetMember) {
        validateOwnerPermission(requesterMember);
        validateMemberInRoom(targetMember);
        managerList.remove(targetMember);
    }

    private void validateMemberInRoom(Member member) {
        matchingInfos.stream()
                .filter(info -> info.isMember(member))
                .findAny()
                .orElseThrow(() -> MEMBER_NOT_IN_ROOM.build(member.getId()));
    }

    public void deactivateMatchingRoom (Member requesterMember) {
        validateOwnerPermission(requesterMember);
        this.isActive = false;
    }

    private void validateOwnerPermission(Member member) {
        if (!Objects.equals(getCreatedId(), member.getId())) {
            throw MANAGER_PERMISSION_DENIED.build(member.getId());
        }
    }

    public void changeMatchingStatus(Member member, MatchingStatus status) {
        MatchingInfo existingInfo = findMatchingInfoByMember(member)
                .orElseThrow(() -> INVALID_MATCHING_ROOM_INFO.build(id, member.getId()));

        existingInfo.changeStatus(status);
    }

    private Optional<MatchingInfo> findMatchingInfoByMember(Member member) {
        return matchingInfos.stream()
                .filter(info -> info.isMember(member))
                .findFirst();
    }

    public void updateRoomName(String roomName) {
        name = roomName;
    }

    public boolean isManager(Member member) {
        return managerList.contains(member);
    }


    public void activate() {
        this.isActive = true;
    }

    public void validateRoomActive() {
        if (!isActive) {
            throw INACTIVE_MATCHING_ROOM.build(this.id);
        }
    }

    public List<Member> findMembersByGroupId(Long groupId) {
        return matchGroups.stream()
                .filter(matchGroup -> Objects.equals(matchGroup.getId(), groupId))
                .findFirst()
                .map(MatchGroup::getMembers)
                .orElseThrow(() -> INVALID_MATCHING_GROUP.build(groupId));
    }

    public void endGroupByGroupId(Long groupId) {
        matchGroups.stream()
                .filter(matchGroup -> Objects.equals(matchGroup.getId(), groupId))
                .findFirst()
                .orElseThrow(() -> INVALID_MATCHING_GROUP.build(groupId))
                .endGame();
    }

    public void replaceMatchGroupMember (Long groupId, Member targetMember, Member replacementMember) {
        matchGroups.stream()
                .filter(matchGroup -> Objects.equals(matchGroup.getId(), groupId))
                .findFirst()
                .orElseThrow(() -> INVALID_MATCHING_GROUP.build(groupId))
                .replaceMember(targetMember, replacementMember);
    }

    public static MatchingRoom createMatchingRoom(String name, Member member) {
        MatchingRoom matchingRoom = new MatchingRoom(name);
        matchingRoom.entryCode = generateEntryCode();
        matchingRoom.isActive = true;
        matchingRoom.managerList.add(member);
        return matchingRoom;
    }

    private static String generateEntryCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void fixture(Long createId) {
        super.fixture(createId);
    }
}
