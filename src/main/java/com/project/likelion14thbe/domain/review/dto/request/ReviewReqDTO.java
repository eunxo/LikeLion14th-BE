package com.project.likelion14thbe.domain.review.dto.request;

public class ReviewReqDTO {
    public record ReviewCreateReq(
            Double rating,
            String content
    ) {
    }
}
