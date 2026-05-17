package com.project.likelion14thbe.domain.member.converter;

import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.enums.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConverter {

    public static Member toMember(MemberReqDTO.SignupReq memberReqDTO, BCryptPasswordEncoder passwordEncoder) {
        final String encodedPassword = passwordEncoder.encode(memberReqDTO.getPassword());

        return Member.builder()
                .email(memberReqDTO.getEmail())
                .password(encodedPassword)
                .name(memberReqDTO.getName())
                .role(Role.ROLE_USER)
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
                .orderCount(0)
                .reviewCount(0)
                .build();
    }
}