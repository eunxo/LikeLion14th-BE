package com.project.likelion14thbe.domain.auth.dto.request;

import lombok.Builder;

public class AuthReqDTO {
    @Builder
    public record LoginReq (
            String email,
            String password
    ){
    }
}
