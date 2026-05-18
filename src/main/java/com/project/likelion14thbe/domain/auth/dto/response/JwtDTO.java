package com.project.likelion14thbe.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "JWT 토큰 응답 DTO")
@Builder
public record JwtDTO(
        @Schema(description = "JWT Access Token", example = "eyJhbGciOi...")
        String accessToken,
        @Schema(description = "JWT Refresh Token", example = "eyJhbGciOi...")
        String refreshToken
) {
}
