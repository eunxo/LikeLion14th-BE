package com.project.likelion14thbe.domain.member.converter;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConverter {

    public static Member toMember(MemberReqDTO.SignUpReqDTO request) {
        return Member.builder()
                .email(request.email())
                .password(request.password())
                .name(request.name())
                .build();
    }

    public static MemberResDTO.SignUpResDTO toSignUpResDTO(Member member) {
        return new MemberResDTO.SignUpResDTO(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getCreatedAt()
        );
    }

    public static MemberResDTO.MyInfoResDTO toMyInfoResDTO(Member member) {
        return new MemberResDTO.MyInfoResDTO(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getCreatedAt()
        );
    }
}
