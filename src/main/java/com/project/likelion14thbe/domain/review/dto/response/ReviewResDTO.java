package com.project.likelion14thbe.domain.review.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDTO {
    @Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class ReviewDetailRes {
        private Long id;
        private String content;
        private Double rating;
        private LocalDateTime createdAt;
    }

    @Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class ReviewListRes {
        private List<ReviewDetailRes> reviews;
    }
}