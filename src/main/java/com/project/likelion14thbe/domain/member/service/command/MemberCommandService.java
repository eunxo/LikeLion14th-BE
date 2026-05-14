package com.project.likelion14thbe.domain.member.service.command;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;

public interface MemberCommandService {

    MemberResDTO.MemberSignupResDTO signup(MemberReqDTO.MemberSignupReqDTO memberSignupReqDTO);

    void updatePassword(Long memberId, MemberReqDTO.PasswordResetDTO dto);

    void deleteMember(Long memberId);
}
