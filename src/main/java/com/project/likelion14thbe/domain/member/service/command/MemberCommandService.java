package com.project.likelion14thbe.domain.member.service.command;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;

public interface MemberCommandService {

    MemberResDTO.MemberCreateRes createMember(MemberReqDTO.MemberCreateReq memberCreateReq);
    void updatePassword(String email, MemberReqDTO.PasswordResetDTO dto);
    void deleteMember(String email);
    void updateProfile(String email, MemberReqDTO.MemberFixReq dto);
}
