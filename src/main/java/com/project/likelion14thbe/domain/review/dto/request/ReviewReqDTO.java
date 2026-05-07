package com.project.likelion14thbe.domain.review.dto.request;

public class ReviewReqDTO {
    public record ReviewCreateReq(
            Long memberId,
            Double rating,
            String content) {
    }
}
