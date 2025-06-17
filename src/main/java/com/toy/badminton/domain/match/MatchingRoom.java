package com.toy.badminton.domain.match;

import com.toy.badminton.domain.BaseTimeEntity;
import com.toy.badminton.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.*;

import static com.toy.badminton.domain.match.MatchingService.DOUBLES;
import static com.toy.badminton.domain.match.MatchingStatus.MATCHED;
import static com.toy.badminton.domain.match.MatchingStatus.WAITING;
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
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "matchingRoom", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<MatchingRoomMember> matchingRoomMembers = new ArrayList<>();

    @Builder.Default
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "matchingRoom", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<MatchGroup> matchGroups = new ArrayList<>();

    @Builder.Default
    @BatchSize(size = 100)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
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


    public List<MatchingRoomMember> getActiveRoomMember() {
        return new ArrayList<>(
                matchingRoomMembers.stream()
                        .filter(MatchingRoomMember::isWaiting)
                        .toList()
        );
    }

    public void validateMinActiveMembers(int minSize) {
        long activeSize = matchingRoomMembers.stream()
                .filter(MatchingRoomMember::isWaiting)
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

    public void addMember(Member member, MatchingRoomMember matchingRoomMember) {
        Optional<MatchingRoomMember> existRoomMember = findMatchingRoomMemberByMember(member);

        if (existRoomMember.isPresent()) {
            existRoomMember.get().changeStatus(WAITING);
            return;
        }

        matchingRoomMembers.add(matchingRoomMember);
    }

    public void addGroup(MatchGroup matchGroup) {
        validateAddGroupMemberCount(matchGroup);
        validateAddGroupMemberAllInRoom(matchGroup);
        matchGroups.add(matchGroup);
        changeStatusInGroup(matchGroup.getMatchingRoomMembers(), MATCHED);
    }

    private void validateAddGroupMemberAllInRoom(MatchGroup matchGroup) {
        Set<MatchingRoomMember> allRoomMember = new HashSet<>(matchingRoomMembers);
        List<MatchingRoomMember> roomMembers = matchGroup.getMatchingRoomMembers();
        if(!allRoomMember.containsAll(roomMembers)) {
            String memberIdsNotFound = roomMembers.stream().map(MatchingRoomMember::getId)
                    .toList().toString();
            throw ROOM_MEMBER_NOT_IN_ROOM.build(memberIdsNotFound);
        }
    }

    private void validateAddGroupMemberCount(MatchGroup matchGroup) {
        int addCount = new HashSet<>(matchGroup.getMatchingRoomMembers()).size();

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
        matchingRoomMembers.stream()
                .filter(roomMember -> roomMember.isMember(member))
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
        MatchingRoomMember roomMember = findMatchingRoomMemberByMember(member)
                .orElseThrow(() -> INVALID_MATCHING_ROOM_INFO.build(id, member.getId()));

        roomMember.changeStatus(status);
    }


    private Optional<MatchingRoomMember> findMatchingRoomMemberByMember(Member member) {
        return matchingRoomMembers.stream()
                .filter(roomMember -> roomMember.isMember(member))
                .findFirst();
    }

    public List<MatchingRoomMember> findMatchingRoomMembersByMembers(List<Member> members) {
        Set<Member> memberSet = new HashSet<>(members);
        return matchingRoomMembers.stream()
                .filter(roomMember -> memberSet.contains(roomMember.getMember()))
                .toList();
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

    public void handleGroupStatusChange(Long groupId, MatchingStatus status) {
        MatchGroup group = matchGroups.stream()
                .filter(matchGroup -> matchGroup.isSameId(groupId))
                .findFirst()
                .orElseThrow(() -> INVALID_MATCHING_GROUP.build(groupId));

        List<MatchingRoomMember> roomMembers = group.getMatchingRoomMembers();

        if (status.isCompleted()) {
            group.endGame();
            changeStatusInGroup(roomMembers, WAITING);
            return;
        }

        changeStatusInGroup(roomMembers, status);
    }

    private void changeStatusInGroup (List<MatchingRoomMember> matchingRoomMembers, MatchingStatus status) {
        matchingRoomMembers.forEach(roomMember -> roomMember.changeStatus(status));
    }

    public void swapGroupMember (Long groupId, Member target, Member replacement) {
        MatchingRoomMember targetRoomMember = findMatchingRoomMemberByMember(target).orElseThrow(() -> REQUESTER_NOT_FOUND.build(target.getId()));
        MatchingRoomMember replacementRoomMember = findMatchingRoomMemberByMember(replacement).orElseThrow(() -> TARGET_NOT_FOUND.build(replacement.getId()));

        matchGroups.stream()
                .filter(matchGroup -> matchGroup.isSameId(groupId))
                .findFirst()
                .orElseThrow(() -> INVALID_MATCHING_GROUP.build(groupId))
                .replaceMember(targetRoomMember, replacementRoomMember);

        targetRoomMember.changeStatus(WAITING);
        replacementRoomMember.changeStatus(MATCHED);
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
