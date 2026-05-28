package com.project.likelion14thbe.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NaverUserInfoResponseDTO(

        @JsonProperty("resultcode")
        String resultCode,

        @JsonProperty("message")
        String message,

        @JsonProperty("response")
        NaverResponse response
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record NaverResponse(

            @JsonProperty("id")
            String id,

            @JsonProperty("email")
            String email,

            @JsonProperty("name")
            String name,

            @JsonProperty("profile_image")
            String profileImage
    ) {
    }
}
