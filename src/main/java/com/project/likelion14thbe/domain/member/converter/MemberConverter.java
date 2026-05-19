package com.project.likelion14thbe.domain.member.converter;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.enums.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConverter {

    public static Member toMember(MemberReqDTO.SignUpReq memberCreateReqDTO, String encodedPassword) {
        return Member.builder()
                .name(memberCreateReqDTO.getName())
                .email(memberCreateReqDTO.getEmail())
                .password(encodedPassword)
                .role(Role.ROLE_USER)
                .build();
    }

    public static Member toKakaoMember(String email, String name, String encodedPassword) {
        return Member.builder()
                .email(email)
                .name(name)
                .password(encodedPassword)
                .provider("kakao")
                .role(Role.ROLE_USER)
                .build();
    }

    public static MemberResDTO.SignUpRes toMemberResponseDTO(Member member) {
        return MemberResDTO.SignUpRes.builder()
                .id(member.getUserId())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static MemberResDTO.MemberPreviewResDTO toMemberPreviewResponseDTO(Member member) {
        return MemberResDTO.MemberPreviewResDTO.builder()
                .id(member.getUserId())
                .name(member.getName())
                .build();
    }

    public static MemberResDTO.MyInfoRes toMyInfoRes(Member member) {
        return MemberResDTO.MyInfoRes.builder()
                .memberId(member.getUserId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }

    public static MemberResDTO.UpdateRes toUpdateRes(Member member) {
        return MemberResDTO.UpdateRes.builder()
                .memberId(member.getUserId())
                .name(member.getName())
                .build();
    }
}


