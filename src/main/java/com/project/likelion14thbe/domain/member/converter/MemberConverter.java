package com.project.likelion14thbe.domain.member.converter;

import com.project.likelion14thbe.domain.auth.dto.response.KakaoUserInfoResponseDTO;
import com.project.likelion14thbe.domain.auth.dto.response.NaverUserInfoResponseDTO;
import com.project.likelion14thbe.domain.member.dto.request.MemberReqDTO;
import com.project.likelion14thbe.domain.member.dto.response.MemberResDTO;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.enums.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConverter {


    public static Member toMember(MemberReqDTO.MemberCreateReq memberCreateReq, BCryptPasswordEncoder passwordEncoder){
        final String encodePassword = passwordEncoder.encode(memberCreateReq.password());
        return Member.builder()
                .email(memberCreateReq.email())
                .password(encodePassword)
                .role(Role.ROLE_USER)
                .name(memberCreateReq.name())
                .build();
    }

    public static Member toKakaoMember(KakaoUserInfoResponseDTO userInfo){
        return Member.builder()
                .name(userInfo.kakaoAccount().profile().nickName())
                .email(userInfo.kakaoAccount().email())
                .role(Role.ROLE_USER)
                .build();
    }

    public static Member toNaverMember(NaverUserInfoResponseDTO userInfo){
        return Member.builder()
                .name(userInfo.response().nickname())
                .email(userInfo.response().email())
                .role(Role.ROLE_USER)
                .build();
    }

    public static MemberResDTO.MemberCreateRes toMemberResponseDTO(Member member){
        return MemberResDTO.MemberCreateRes.builder()
                .id(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static MemberResDTO.MemberGetRes toMemberGetRes(Member member){
        return MemberResDTO.MemberGetRes.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }


}
