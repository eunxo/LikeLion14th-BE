package com.project.likelion14thbe.domain.member.converter;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

//생성자 접근 비허용
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConverter {

    public static Member toMember(MemberReqDTO.SignupReq memberReqDTO) {
        return Member.builder()
                .email(memberReqDTO.getEmail())
                .password(memberReqDTO.getPassword())
                .name(memberReqDTO.getName())
                .profileImage(memberReqDTO.getProfileImage())
                .build();
    }

    public static MemberResDTO.ProfileRes toMemberResDTO(Member member) {
        return MemberResDTO.ProfileRes.builder()
                .id(member.getId())
                .createdAt(member.getCreatedAt())
                .profileImage(member.getProfileImage())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }

}
