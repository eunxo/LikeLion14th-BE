package com.project.likelion14thbe.domain.member.dto.request;

import lombok.*;

public class MemberReqDTO {

    @Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class SignupReq {
        private String name;
        private String email;
        private String password;
        private String profileImage;
    }

    @Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class LoginReq {
        private String email;
        private String password;
    }

    @Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class KakaoLoginReq {
        private String kakaoAccessToken;
    }

    @Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class UpdateReq {
        private String name;
        private String email;
        private String profileImage;
    }
}