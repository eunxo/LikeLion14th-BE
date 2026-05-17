package com.project.likelion14thbe.domain.review.dto.request;

import java.time.LocalDateTime;

public class ReviewReqDTO {
    public record ReviewCreateReq(
            Double rating,
            String content
    ) {
    }

    public record ReviewUpdateReq(
            Double rating,
            String content
    ){
    }
}
