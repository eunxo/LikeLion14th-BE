package com.project.likelion14thbe.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberResDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "로그인 응답 DTO")
    public static class LoginRes {
        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        private String accessToken;

        @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        private String refreshToken;

        @Schema(description = "사용자 이름", example = "최은서")
        private String name;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "카카오 로그인 응답 DTO")
    public static class KakaoLoginRes {
        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        private String accessToken;

        @Schema(description = "사용자 이름", example = "최은서")
        private String name;

        @Schema(description = "이메일", example = "eunseo_kakao@gmail.com")
        private String email;

        @Schema(description = "신규 회원 여부", example = "false")
        private Boolean isNewMember;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "마이페이지(내 정보) 정보 응답 DTO")
    public static class MemberProfileRes {

        @Schema(description = "사용자 실명", example = "최은서")
        private String name;

        @Schema(description = "이메일 주소", example = "eunseo@sangmyung.ac.kr")
        private String email;

        @Schema(description = "총 주문 횟수", example = "12")
        private Integer orderCount;

        @Schema(description = "작성한 리뷰 개수", example = "5")
        private Integer reviewCount;

        @Schema(description = "프로필 이미지 URL", example = "https://image.com/profile/eunseo.png")
        private String profileImage;
    }
}