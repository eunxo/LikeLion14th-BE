package com.project.likelion14thbe.domain.auth.dto.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NaverTokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("token_type") String tokenType,
        // 네이버 토큰 응답의 expires_in은 숫자 문자열로 내려오므로 String으로 받는다
        @JsonProperty("expires_in") String expiresIn,
        @JsonProperty("error") String error,
        @JsonProperty("error_description") String errorDescription
) {
}
