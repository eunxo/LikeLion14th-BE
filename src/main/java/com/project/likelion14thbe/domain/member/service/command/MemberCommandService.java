package com.project.likelion14thbe.domain.member.service.command;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;

public interface MemberCommandService {

    MemberResDTO.SignUpRes signUp(MemberReqDTO.SignUpReq req);

    MemberResDTO.UpdateRes updateMyInfo(String email, MemberReqDTO.UpdateReq req);

    void deleteMember(Long memberId);
    void updatePassword(Long memberId, MemberReqDTO.PasswordResetDTO dto);
    long purgeExpiredDeletedMembers();
}
