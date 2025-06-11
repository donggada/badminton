package com.toy.badminton.domain.service.match;

import com.toy.badminton.application.dto.param.ChangeGroupParameters;
import com.toy.badminton.application.dto.request.manager.GroupStatusChangeRequest;
import com.toy.badminton.domain.factory.matching.MatchingType;
import com.toy.badminton.domain.model.member.Member;

import java.util.List;

public interface MatchService {
    void changeMatchingGroupMemberStatus(ChangeGroupParameters parameters, Member targetMember, Member replacementMember);

    void participationRoom(Long roomId, Member requestMember, String roomName);

    void addManagerRole(Long roomId, Member addMember , Member requestMember);

    void removeManagerRole(Long roomId, Member removeMember, Member requestMember);

    void deactivateMatchingRoom(Long roomId, Member requestMember);

    void handleTypeMatching(Long roomId, Member member, MatchingType type);

    void handleCustomMatching(Long roomId, Member requestMember, List<Member> members);

    void changeGroupStatus (Long roomId, Member requestMember, GroupStatusChangeRequest request);
}
