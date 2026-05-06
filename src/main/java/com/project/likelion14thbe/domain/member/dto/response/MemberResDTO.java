package com.project.likelion14thbe.domain.member.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public class MemberResDTO {

    @Builder
    public record MemberLoginRes(
            String accessToken,
            String refreshToken
    ){

    }

    @Builder
    public record MemberGetRes(
            Long id,
            String name,
            String email
    ){

    }

    @Builder
    public record MemberCreateRes(
            Long id,
            LocalDateTime createdAt
    ){

    }
}
