package com.project.likelion14thbe.domain.member.dto.response;

import lombok.Builder;

public class MemberResDTO {

    @Builder
    public record UserTokenRes(
            String accessToken,
            String refreshToken
    ) {
    }
}
