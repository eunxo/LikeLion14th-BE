package com.project.likelion14thbe.domain.member.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public class MemberResDTO {

    @Builder
    public record UserTokenRes(
            String accessToken,
            String refreshToken
    ) {
    }

    @Builder
    public record MemberSignupResDTO(
            Long id,
            LocalDateTime createdAt
    ) {
    }

    @Builder
    public record MemberPreviewResDTO(
            Long id,
            String name,
            String email
    ) {
    }
}
