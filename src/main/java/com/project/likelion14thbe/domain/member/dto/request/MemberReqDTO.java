package com.project.likelion14thbe.domain.member.dto.request;

import lombok.Getter;
import lombok.Setter;

public class MemberReqDTO {

    public record MemberCreateReq(
            String email,
            String password,
            String name
    ) {

    }

    public record MemberLoginReq(
            String email,
            String password
    ){

    }

    public record MemberFixReq(
            String name,
            String email,
            String photo
    ){

    }
}
