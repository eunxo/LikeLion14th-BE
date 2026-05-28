package com.project.likelion14thbe.domain.naver.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NaverUserInfoResponseDTO(
        @JsonProperty("resultcode") String resultCode,
        @JsonProperty("message") String message,
        @JsonProperty("response") NaverAccount response
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record NaverAccount(
            @JsonProperty("id") String id,
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("profile_image") String profileImage
    ) {}
}