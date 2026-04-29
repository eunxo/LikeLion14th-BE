package com.project.likelion14thbe.domain.member.dto.response;

import lombok.Builder;

public class MemberResDTO {

    @Builder
    public record MemberLoginRes(
            String accessToken,
            String refreshToken
    ){

    }

    @Builder
    public record MemberGetRes(
            String name,
            String email
    ){

    }
}
