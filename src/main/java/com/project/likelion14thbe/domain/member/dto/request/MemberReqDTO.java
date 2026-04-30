package com.project.likelion14thbe.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class MemberReqDTO {

    @Getter
    @Setter
    @Schema(description = "로그인 요청 DTO")
    public static class LoginReq {
        @Schema(description = "이메일", example = "eunseo@sangmyung.ac.kr")
        private String email;

        @Schema(description = "비밀번호", example = "password123!")
        private String password;
    }

    @Getter
    @Setter
    @Schema(description = "카카오 로그인 요청 DTO")
    public static class KakaoLoginReq {
        @Schema(description = "카카오 엑세스 토큰", example = "KAKAOTOKEN_EXAMPLE_12345")
        private String accessToken;
    }

    @Getter
    @Setter
    @Schema(description = "회원가입 요청 DTO")
    public static class SignUpReq {
        @Schema(description = "이메일", example = "eunseo@sangmyung.ac.kr")
        private String email;

        @Schema(description = "비밀번호", example = "password123!")
        private String password;

        @Schema(description = "이름", example = "최은서")
        private String name;
    }

    @Getter
    @Setter
    @Schema(description = "비밀번호 수정 요청 DTO")
    public static class PasswordUpdateReq {
        @Schema(description = "현재 비밀번호", example = "current_password123!")
        private String oldPassword;

        @Schema(description = "새 비밀번호", example = "new_secure_password456!")
        private String newPassword;

        @Schema(description = "새 비밀번호 확인", example = "new_secure_password456!")
        private String confirmPassword;
    }
}
