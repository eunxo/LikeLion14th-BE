package com.project.likelion14thbe.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class MemberResDTO {

    @Schema(description = "회원가입 응답 DTO")
    public record SignUpResDTO(
            @Schema(description = "회원 ID", example = "1")
            Long memberId,
            @Schema(description = "이메일", example = "user@example.com")
            String email,
            @Schema(description = "닉네임", example = "bro")
            String nickname,
            @Schema(description = "가입 일시", example = "2026-04-29T10:00:00")
            LocalDateTime createdAt
    ) {
    }

    @Schema(description = "일반 로그인 응답 DTO")
    public record LoginResDTO(
            @Schema(description = "회원 ID", example = "1")
            Long memberId,
            @Schema(description = "닉네임", example = "bro")
            String nickname,
            @Schema(description = "JWT Access Token", example = "eyJhbGciOi...")
            String accessToken,
            @Schema(description = "JWT Refresh Token", example = "eyJhbGciOi...")
            String refreshToken,
            @Schema(description = "Access Token 만료 시간(초)", example = "3600")
            Long expiresIn
    ) {
    }

    @Schema(description = "카카오 로그인 응답 DTO")
    public record KakaoLoginResDTO(
            @Schema(description = "회원 ID", example = "1")
            Long memberId,
            @Schema(description = "닉네임", example = "bro")
            String nickname,
            @Schema(description = "JWT Access Token", example = "eyJhbGciOi...")
            String accessToken,
            @Schema(description = "JWT Refresh Token", example = "eyJhbGciOi...")
            String refreshToken,
            @Schema(description = "신규 회원 여부 (카카오 첫 로그인 시 true)", example = "false")
            Boolean isNewMember
    ) {
    }

    @Schema(description = "비밀번호 수정 응답 DTO")
    public record UpdatePasswordResDTO(
            @Schema(description = "회원 ID", example = "1")
            Long memberId,
            @Schema(description = "수정 일시", example = "2026-04-29T10:30:00")
            LocalDateTime updatedAt
    ) {
    }

    @Schema(description = "내 정보 조회 응답 DTO")
    public record MyInfoResDTO(
            @Schema(description = "회원 ID", example = "1")
            Long memberId,
            @Schema(description = "이메일", example = "user@example.com")
            String email,
            @Schema(description = "닉네임", example = "bro")
            String nickname,
            @Schema(description = "가입 일시", example = "2026-04-29T10:00:00")
            LocalDateTime createdAt
    ) {
    }
}
