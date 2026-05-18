package com.project.likelion14thbe.domain.member.service.command;

import com.project.likelion14thbe.domain.auth.dto.response.KakaoUserInfoResponseDTO;
import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;

public interface MemberCommandService {

    MemberResDTO.MemberSignupResDTO signup(MemberReqDTO.MemberSignupReqDTO memberSignupReqDTO);

    void updatePassword(CustomUserDetails customUserDetails, MemberReqDTO.PasswordResetDTO dto);

    void deleteMember(CustomUserDetails customUserDetails);

    Member kakaoSignup(KakaoUserInfoResponseDTO userInfo);
}
