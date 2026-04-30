package com.project.likelion14thbe.domain.review.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public class ReviewResDTO {

    @Builder
    public record ReviewDetailRes(
        Long reviewId,
        Double rating,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String nickname,
        String profileImg
    ){
    }
}
