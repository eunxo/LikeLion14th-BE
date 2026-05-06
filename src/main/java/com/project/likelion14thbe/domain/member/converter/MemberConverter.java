package com.project.likelion14thbe.domain.member.converter;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConverter {

    public static Member toMember(MemberReqDTO.MemberCreateReq memberCreateReq){
        return Member.builder()
                .email(memberCreateReq.email())
                .password(memberCreateReq.password())
                .name(memberCreateReq.name())
                .build();
    }

    public static MemberResDTO.MemberCreateRes toMemberResponceDTO(Member member){
        return MemberResDTO.MemberCreateRes.builder()
                .id(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static MemberResDTO.MemberGetRes toMemberGetRes(Member member){
        return MemberResDTO.MemberGetRes.builder()
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }


}
