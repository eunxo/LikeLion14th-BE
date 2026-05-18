package com.project.likelion14thbe.domain.member.dto.request;

import lombok.Builder;

@Builder
public record LoginReq(
        String email,
        String password
) {
}
