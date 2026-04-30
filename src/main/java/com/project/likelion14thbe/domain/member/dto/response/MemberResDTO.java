package com.project.likelion14thbe.domain.member.dto.response;

import lombok.*;

public class MemberResDTO {

    @Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class ProfileRes {
        private String name;
        private String email;
        private String profileImage;
        private Integer orderCount;
        private Integer reviewCount;
    }
}