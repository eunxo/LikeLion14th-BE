package com.project.likelion14thbe.domain.auth.converter;

import com.project.likelion14thbe.domain.auth.dto.oauth.OAuthUserInfo;
import com.project.likelion14thbe.domain.auth.entity.SocialAccount;
import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.enums.Role;

public class OAuthConverter {

    private OAuthConverter() {
    }

    // encodedPassword: Member.password(nullable=false) 스키마 호환용 BCrypt(UUID) 값.
    // 사용자가 아는 비밀번호가 아니므로 일반 로그인 자격증명으로 취급하지 않는다.
    public static Member toMember(OAuthUserInfo info, String encodedPassword) {
        String name = (info.nickname() != null && !info.nickname().isBlank())
                ? info.nickname()
                : localPartOf(info.email());
        return Member.builder()
                .name(name)
                .email(info.email())
                .password(encodedPassword)
                .profileImage(info.profileImage())
                .role(Role.ROLE_USER)
                .build();
    }

    public static SocialAccount toSocialAccount(OAuthUserInfo info, Member member) {
        return SocialAccount.builder()
                .provider(info.provider())
                .providerId(info.providerId())
                .member(member)
                .build();
    }

    private static String localPartOf(String email) {
        int at = email.indexOf('@');
        return at > 0 ? email.substring(0, at) : email;
    }
}
