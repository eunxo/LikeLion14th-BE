package com.project.likelion14thbe.domain.member.service.command;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;

public interface MemberCommandService {

    MemberResDTO.ProfileRes signUp(MemberReqDTO.SignupReq signupReq);

    void updatePassword(String email, MemberReqDTO.PasswordResetDTO dto);

    void deleteMember(String email);

    void updateMember(String email, MemberReqDTO.UpdateReq updateReq);
}