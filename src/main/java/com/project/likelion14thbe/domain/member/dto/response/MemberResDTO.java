package com.project.likelion14thbe.domain.member.dto.response;

import lombok.*;

import java.time.LocalDateTime;

public class MemberResDTO {

    @Builder @Getter
    public static class ProfileRes {
        private String name;
        private String email;
        private String profileImage;
        private Integer orderCount;
        private Integer reviewCount;
        private Long id;
        private LocalDateTime createdAt;
    }
}