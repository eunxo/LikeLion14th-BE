package com.project.likelion14thbe.domain.auth.dto.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NaverUserInfoResponse(
        @JsonProperty("resultcode") String resultCode,
        @JsonProperty("message") String message,
        @JsonProperty("response") NaverAccount response
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record NaverAccount(
            @JsonProperty("id") String id,
            @JsonProperty("email") String email,
            @JsonProperty("nickname") String nickname,
            @JsonProperty("name") String name,
            @JsonProperty("profile_image") String profileImage
    ) {
    }
}
