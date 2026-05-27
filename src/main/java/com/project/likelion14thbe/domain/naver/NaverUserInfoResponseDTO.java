package com.project.likelion14thbe.domain.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NaverUserInfoResponseDTO(

        @JsonProperty("resultcode")
        String resultCode,

        @JsonProperty("message")
        String message,

        @JsonProperty("response")
        Response response
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Response(

            @JsonProperty("id")
            String id,

            @JsonProperty("email")
            String email,

            @JsonProperty("name")
            String name
    ) {
    }
}