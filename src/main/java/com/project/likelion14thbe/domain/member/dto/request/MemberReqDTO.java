package com.project.likelion14thbe.domain.member.dto.request;

import lombok.Builder;

public class MemberReqDTO {
    public record MemberSignupReqDTO(
            String name,
            String email,
            String password
    ) {
    }

    @Builder
    public record LoginReq (
            String email,
            String password
    ){
    }

    public record UserLoginKakaoReq(
            String kakaoAccessToken
    ) {
    }

    public record PasswordResetDTO(
            String password
    ){
    }
}
