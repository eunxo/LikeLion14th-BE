package com.project.likelion14thbe.domain.member.converter;

import com.project.likelion14thbe.domain.auth.dto.response.KakaoUserInfoResponseDTO;
import com.project.likelion14thbe.domain.auth.dto.response.NaverUserInfoResponseDTO;
import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.enums.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConverter {

    public static Member toMember(MemberReqDTO.MemberSignupReqDTO memberSignupReqDTO, String encodePassword) {
        return Member.builder()
                .name(memberSignupReqDTO.name())
                .email(memberSignupReqDTO.email())
                .password(encodePassword)
                .role(Role.ROLE_USER)
                .build();
    }

    public static MemberResDTO.MemberSignupResDTO toMemberResDTO(Member member) {
        return MemberResDTO.MemberSignupResDTO.builder()
                .id(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static MemberResDTO.MemberPreviewResDTO toMemberPreviewResDTO(Member member) {
        return MemberResDTO.MemberPreviewResDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }

    public static Member kakaoToMember(KakaoUserInfoResponseDTO userInfo) {
        return Member.builder()
                .email(userInfo.kakaoAccount().email())
                .name(userInfo.kakaoAccount().profile().nickName())
                .profileImage(userInfo.kakaoAccount().profile().profileImageUrl())
                .socialProvider(Boolean.TRUE)
                .role(Role.ROLE_USER)
                .build();
    }

    public static Member naverToMember(NaverUserInfoResponseDTO userInfo) {
        return Member.builder()
                .email(userInfo.response().email())
                .name(userInfo.response().name())
                .profileImage(userInfo.response().profileImage())
                .socialProvider(Boolean.TRUE)
                .role(Role.ROLE_USER)
                .build();
    }

}
