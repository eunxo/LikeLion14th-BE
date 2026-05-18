package com.project.likelion14thbe.global.security.jwt.dto;

import lombok.Builder;

@Builder
public record JwtDTO(
        String accessToken,
        String refreshToken
) {



}
