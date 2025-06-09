package com.toy.badminton.application.facade;

import com.toy.badminton.application.dto.request.ChangeMatchingStatusRequest;
import com.toy.badminton.application.dto.request.CreateMatchingRoomRequest;
import com.toy.badminton.application.dto.response.matching.CreateMatchingRoomResponse;
import com.toy.badminton.application.dto.response.matching.MatchingRoomResponse;
import com.toy.badminton.application.dto.response.matching.enterMatch.MatchingRoomDetailResponse;
import com.toy.badminton.domain.model.match.matchingInfo.MatchingInfo;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.domain.service.MatchingInfoService;
import com.toy.badminton.domain.service.MatchingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingFacade {

    private final MatchingRoomService matchingRoomService;


    public CreateMatchingRoomResponse createRoom(CreateMatchingRoomRequest request, Member member) {
        return CreateMatchingRoomResponse.of(matchingRoomService.createRoom(request.roomName(), member));
    }

    @Transactional
    public void changeMatchingStatus (Long matchingRoomId, Member member, ChangeMatchingStatusRequest request) {
        MatchingRoom matchingRoom = matchingRoomService.findActiveRoom(matchingRoomId);
        matchingRoom.changeMatchingStatus(member, request.status());
    }

    @Transactional
    public MatchingRoomDetailResponse enterMatchingRoom(Long roomId, Member member) {
        MatchingRoom matchingRoom = matchingRoomService.findActiveRoom(roomId);
        matchingRoom.addMember(member, MatchingInfo.createMatchingInfo(matchingRoom, member));
        return MatchingRoomDetailResponse.of(matchingRoom, member);
    }

    @Transactional
    public MatchingRoomDetailResponse enterCodeMatchingRoom(String entryCode, Member member) {
        MatchingRoom matchingRoom = matchingRoomService.findActiveRoomByEntryCode(entryCode);
        matchingRoom.addMember(member, MatchingInfo.createMatchingInfo(matchingRoom, member));
        return MatchingRoomDetailResponse.of(matchingRoom, member);
    }

    public MatchingRoomDetailResponse getMatchingRoomDetail(Long roomId, Member member) {
        return MatchingRoomDetailResponse.of(matchingRoomService.findActiveRoom(roomId), member);
    }

    @Transactional(readOnly = true)
    public List<MatchingRoomResponse> getMatchingRoomList() {
        return matchingRoomService.findMatchingRoomList().stream().map(MatchingRoomResponse::of).toList();
    }

}
