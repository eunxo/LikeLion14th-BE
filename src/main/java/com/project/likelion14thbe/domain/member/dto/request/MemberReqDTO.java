package com.project.likelion14thbe.domain.member.dto.request;

public class MemberReqDTO {
    public record MemberSignupReqDTO(
            String name,
            String email,
            String password
    ) {
    }

    public record UserLoginReq(
            String email,
            String password
    ) {
    }

    public record UserLoginKakaoReq(
            String kakaoAccessToken
    ) {
    }
}
