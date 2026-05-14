package com.project.likelion14thbe.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class MemberResDTO {

    @Builder
    public record SignUpRes(
            @Schema(description = "회원 ID", example = "1")
            Long id,
            @Schema(description = "가입 일시", example = "2026-05-13T10:00:00")
            LocalDateTime createdAt
    ) {
    }


    @Builder
    public record MemberPreviewResDTO(
            Long id,
            String name
    ) {
    }


    @Getter
    @Builder
    public static class LoginRes {

        @Schema(description = "성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "USER200")
        private String code;

        @Schema(description = "응답 메시지", example = "로그인 성공")
        private String message;
    }

    @Getter
    @Builder
    public static class KakaoLoginRes {

        @Schema(description = "성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "USER200")
        private String code;

        @Schema(description = "응답 메시지", example = "카카오 로그인 성공")
        private String message;

        @Schema(description = "응답 데이터")
        private KakaoLoginResult result;
    }

    @Getter
    @Builder
    public static class KakaoLoginResult {

        @Schema(description = "사용자 ID", example = "1")
        private Long userId;

        @Schema(description = "이름", example = "홍길동")
        private String name;

        @Schema(description = "이메일", example = "kakao@test.com")
        private String email;
    }

    @Getter
    @Builder
    public static class LogoutRes {

        @Schema(description = "성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "USER200")
        private String code;

        @Schema(description = "응답 메시지", example = "로그아웃 성공")
        private String message;
    }

    @Getter
    @Builder
    public static class MyInfoRes {

        @Schema(description = "회원 ID", example = "1")
        private Long memberId;

        @Schema(description = "이름", example = "홍길동")
        private String name;

        @Schema(description = "이메일", example = "test@test.com")
        private String email;
    }

    @Getter
    @Builder
    public static class UpdateRes {

        @Schema(description = "회원 ID", example = "1")
        private Long memberId;

        @Schema(description = "수정된 이름", example = "홍길동")
        private String name;
    }

    @Getter
    @Builder
    public static class DeleteRes {

        @Schema(description = "성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "USER200")
        private String code;

        @Schema(description = "응답 메시지", example = "회원 탈퇴 성공")
        private String message;
    }
}