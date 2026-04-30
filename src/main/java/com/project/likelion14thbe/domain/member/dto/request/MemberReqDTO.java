package com.project.likelion14thbe.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class MemberReqDTO {

    @Getter
    public static class SignUpReq {

        @Schema(description = "이름", example = "홍길동")
        private String name;

        @Schema(description = "이메일", example = "test@test.com")
        private String email;

        @Schema(description = "비밀번호", example = "1234")
        private String password;
    }

    @Getter
    public static class LoginReq {

        @Schema(description = "이메일", example = "test@test.com")
        private String email;

        @Schema(description = "비밀번호", example = "1234")
        private String password;
    }

    @Getter
    public static class KakaoLoginReq {

        @Schema(description = "카카오 액세스 토큰", example = "kakao_access_token")
        private String kakaoAccessToken;
    }

    @Getter
    public static class UpdateReq {

        @Schema(description = "이름", example = "홍길동")
        private String name;

        @Schema(description = "비밀번호", example = "1234")
        private String password;
    }
}