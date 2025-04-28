package com.toy.badminton.domain.service;

import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoom;
import com.toy.badminton.domain.model.match.matchingRoom.MatchingRoomRepository;
import com.toy.badminton.domain.model.member.Member;
import com.toy.badminton.infrastructure.exception.ApplicationException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class MatchingRoomServiceTest {

    @InjectMocks
    private MatchingRoomService matchingRoomService;

    @Mock
    private MatchingRoomRepository matchingRoomRepository;

    @Test
    @DisplayName("성공 - 매칭방 관리자일 경우 매칭방을 반환한다")
    void findManageMatchingRoom_success_whenManager() {
        Long roomId = 1L;
        Member member = Member.builder().id(1L).build();
        Member manager = Member.builder().id(1L).build();
        MatchingRoom room = MatchingRoom.builder().managerList(Set.of(manager)).build();

        given(matchingRoomRepository.findWithAllById(roomId)).willReturn(Optional.of(room));

        MatchingRoom result = matchingRoomService.findManageMatchingRoom(roomId, member);

        assertThat(result).isEqualTo(room);
    }

    @Test
    @DisplayName("실패 - 매칭방 관리자가 아닌 경우 예외가 발생한다")
    void findManageMatchingRoom_fail_whenNotManager() {
        Long roomId = 1L;
        Member member = Member.builder().id(1L).build();
        Member manager = Member.builder().id(2L).username("test").build();
        MatchingRoom room = MatchingRoom.builder().managerList(Set.of(manager)).build();

        given(matchingRoomRepository.findWithAllById(roomId)).willReturn(Optional.of(room));

        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> matchingRoomService.findManageMatchingRoom(roomId, member)
        );


        assertEquals("매칭 수정권한이 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("실패 - 매칭방이 존재하지 않을 경우 예외가 발생한다")
    void findManageMatchingRoom_fail_whenRoomNotFound() {
        Long roomId = 1L;
        Member member = Member.builder().id(1L).build();

        given(matchingRoomRepository.findWithAllById(roomId)).willReturn(Optional.empty());

        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> matchingRoomService.findManageMatchingRoom(roomId, member)
        );

        assertEquals("존재하지 않는 매칭방 입니다.", exception.getMessage());
    }
}