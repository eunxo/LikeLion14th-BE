package com.project.likelion14thbe.domain.member.converter;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConverter {

    public static Member toMember(MemberReqDTO.SignUpReq memberCreateReqDTO) {
        return Member.builder()
                .name(memberCreateReqDTO.getName())
                .email(memberCreateReqDTO.getEmail())
                .password(memberCreateReqDTO.getPassword())
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
}


