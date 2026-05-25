package com.project.likelion14thbe.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.HashMap;

// DTO에 정의되어 있지 않은 값을 받으면 무시하겠다는 애노테이션
@JsonIgnoreProperties(ignoreUnknown = true)
public record NaverUserInfoResponseDTO(

        //회원 번호
        @JsonProperty("resultcode")
        String resultCode,

        // 호출 결과 메시지
        @JsonProperty("message")
        String message,

        //사용자 정보 담음
        @JsonProperty("response")
        Response response
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Response(

            // 네이버 고유 식별자 (카카오와 달리 Long이 아니라 고유한 해시 String 값입니다)
            @JsonProperty("id")
            String id,

            @JsonProperty("email")
            String email,

            @JsonProperty("name")
            String name,

            @JsonProperty("nickname")
            String nickname,

            @JsonProperty("profile_image")
            String profileImage,

            @JsonProperty("gender")
            String gender, // F: 여성, M: 남성, U: 확인불가

            @JsonProperty("age")
            String age, // 연령대 (예: 20-29)

            @JsonProperty("birthyear")
            String birthyear, // 출생연도 (예: 2002)

            @JsonProperty("birthday")
            String birthday, // 생일 (MM-DD 형식)

            @JsonProperty("mobile")
            String mobile // 휴대전화번호 (예: 010-0000-0000)
    ) {
    }
}