package com.project.likelion14thbe.domain.member.service.command;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;

public interface MemberCommandService {

    MemberResDTO.SignUpResDTO signUp(MemberReqDTO.SignUpReqDTO request);

    void updatePassword(String email, MemberReqDTO.PasswordResetDTO dto);

    void deleteMember(String email);
}
