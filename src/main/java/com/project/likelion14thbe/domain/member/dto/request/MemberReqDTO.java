package com.project.likelion14thbe.domain.member.dto.request;

public class MemberReqDTO {
    public record UserSignupReq(
            String name,
            String email,
            String password
    ) {
    }
}
