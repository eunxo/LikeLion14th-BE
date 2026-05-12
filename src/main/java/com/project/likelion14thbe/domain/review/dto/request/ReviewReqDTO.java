package com.project.likelion14thbe.domain.review.dto.request;

import lombok.Builder;

public class ReviewReqDTO {

    public record ReviewCreateReq(
            String reviewContent,
            Double reviewRating
    ) {

    }

    @Builder
    public record ReviewFixReq(
            String content
    ){

    }

    public record ReviewChangeReq(
            String reviewContent,
            Double reviewRating
    ){

    }
}
