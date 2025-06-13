package com.toy.badminton.application.match;

import com.toy.badminton.domain.member.MemberService;
import com.toy.badminton.presentation.match.vo.ChangeGroupParameters;
import com.toy.badminton.presentation.match.request.ChangeGroupRequest;
import com.toy.badminton.domain.member.Member;
import com.toy.badminton.domain.match.MatchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManageFacadeTest {
    @InjectMocks
    private ManageFacade manageFacade;

    @Mock
    private MatchService matchService;
    @Mock
    private MemberService memberService;

//    @Test
    @DisplayName("replaceMatchGroupMember_정상적으로호출된다()")
    void replaceMatchGroupMember() {
        Long roomId = 1L;
        Long targetMemberId = 100L;
        Long replacementMemberId = 200L;
        Long groupId = 10L;
        Member member = Member.builder().id(1L).build();
        Member targetMember = Member.builder().id(targetMemberId).build();
        Member replacementMember = Member.builder().id(replacementMemberId).build();
        ChangeGroupRequest request = new ChangeGroupRequest(replacementMemberId, targetMemberId);

        ChangeGroupParameters parameters = ChangeGroupParameters.builder()
                .roomId(roomId)
                .groupId(groupId)
                .member(member)
                .request(request)
                .build();

        given(memberService.findMember(targetMemberId)).willReturn(targetMember);
        given(memberService.findMember(replacementMemberId)).willReturn(replacementMember);

        manageFacade.replaceMatchGroupMember(parameters);

        verify(memberService, times(1)).findMember(targetMemberId);
        verify(memberService, times(1)).findMember(replacementMemberId);

        verifyNoMoreInteractions(memberService);
    }
}