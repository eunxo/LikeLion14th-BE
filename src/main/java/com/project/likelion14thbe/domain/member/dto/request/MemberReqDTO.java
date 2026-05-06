package com.project.likelion14thbe.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    @Schema(description = "회원가입 요청 DTO")
    public static class MemberCreateReqDTO {
        @Schema(description = "이메일", example = "eunseo@sangmyung.ac.kr")
        private String email;

        @Schema(description = "비밀번호", example = "password123!")
        private String password;

        @Schema(description = "이름", example = "최은서")
        private String name;

        @Schema(description = "나이", example = "14")
        private String age;
    }

}

