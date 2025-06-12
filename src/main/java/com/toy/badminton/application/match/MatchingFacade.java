package com.toy.badminton.application.match;

import com.toy.badminton.domain.match.MatchingInfo;
import com.toy.badminton.presentation.match.request.ChangeMatchingStatusRequest;
import com.toy.badminton.presentation.match.request.CreateMatchingRoomRequest;
import com.toy.badminton.presentation.match.response.CreateMatchingRoomResponse;
import com.toy.badminton.presentation.match.response.MatchingRoomResponse;
import com.toy.badminton.presentation.match.response.MatchingRoomDetailResponse;
import com.toy.badminton.domain.match.MatchingRoom;
import com.toy.badminton.domain.member.Member;
import com.toy.badminton.domain.match.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingFacade {

    private final MatchService matchService;

    @Transactional
    public CreateMatchingRoomResponse createRoom(CreateMatchingRoomRequest request, Member requestMember) {
        return CreateMatchingRoomResponse.of(
                matchService.createRoom(request.roomName(), requestMember)
        );
    }

    @Transactional
    public void changeMatchingStatus (Long roomId, Member requestMember, ChangeMatchingStatusRequest request) {
        MatchingRoom matchingRoom = matchService.findAndValidateActiveRoom(roomId);
        matchingRoom.changeMatchingStatus(requestMember, request.status());
    }

    @Transactional
    public MatchingRoomDetailResponse enterMatchingRoom(Long roomId, Member requestMember) {
        MatchingRoom matchingRoom = matchService.findAndValidateActiveRoom(roomId);
        matchingRoom.addMember(requestMember, MatchingInfo.createMatchingInfo(matchingRoom, requestMember));
        return MatchingRoomDetailResponse.of(matchingRoom, requestMember);
    }

    @Transactional
    public MatchingRoomDetailResponse enterCodeMatchingRoom(String entryCode, Member requestMember) {
        MatchingRoom matchingRoom = matchService.findActiveRoomByEntryCode(entryCode);
        matchingRoom.addMember(requestMember, MatchingInfo.createMatchingInfo(matchingRoom, requestMember));
        return MatchingRoomDetailResponse.of(matchingRoom, requestMember);
    }

    public MatchingRoomDetailResponse getMatchingRoomDetail(Long roomId, Member requestMember) {
        return MatchingRoomDetailResponse.of(matchService.findActiveRoom(roomId), requestMember);
    }


    public List<MatchingRoomResponse> getMatchingRoomList() {
        return matchService.findMatchingRoomList().stream().map(MatchingRoomResponse::of).toList();
    }

}
