package com.project.likelion14thbe.domain.review.dto.response;

import lombok.Builder;

public class ReviewResDTO {

    @Builder
    public record ReviewDetailRes (
           String content,
           Double rating
    ) {
    }
}
