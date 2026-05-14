package com.project.likelion14thbe.domain.member.dto.request;

import lombok.*;

public class MemberReqDTO {

    @Getter
    public static class SignupReq {
        private String name;
        private String email;
        private String password;
        private String profileImage;
    }

    @Getter
    public static class LoginReq {
        private String email;
        private String password;
    }

    @Getter
    public static class KakaoLoginReq {
        private String kakaoAccessToken;
    }

    @Getter
    public static class UpdateReq {
        private String name;
        private String email;
        private String profileImage;
    }

    @Getter
    public static class PasswordResetDTO {
        private String password;
    }
}
