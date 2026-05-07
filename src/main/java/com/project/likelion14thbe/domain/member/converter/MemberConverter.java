package com.project.likelion14thbe.domain.member.converter;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConverter {

    public static Member toMember(MemberReqDTO.MemberSignupReqDTO memberSignupReqDTO) {
        return Member.builder()
                .name(memberSignupReqDTO.name())
                .email(memberSignupReqDTO.email())
                .password(memberSignupReqDTO.password())
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
}
