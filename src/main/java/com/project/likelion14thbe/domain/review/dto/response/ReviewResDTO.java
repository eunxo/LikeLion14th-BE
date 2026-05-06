package com.project.likelion14thbe.domain.review.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResDTO {

    @Builder
    public record ReviewCreateRes(
            Long reviewId,
            LocalDateTime createdAt
    ){

    }

    @Builder
    public record ReviewDetailRes(
            String content,
            Double rating
    ){

    }

    @Builder
    public record ReviewGetRes(
            List<ReviewInfo> datalist
    ) {

        @Builder
        public record ReviewInfo(
                Long reviewId,
                String content,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                Double rating,
                String nickname
        ) {
        }
    }
}
