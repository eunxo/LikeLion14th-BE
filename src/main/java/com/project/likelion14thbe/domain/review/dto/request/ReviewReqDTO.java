package com.project.likelion14thbe.domain.review.dto.request;

import lombok.Getter;
import lombok.Setter;

public class ReviewReqDTO {

    public record ReviewCreateReq (
            String content,
            Double rating
    ) {
    }

    @Getter
    @Setter
    public static class ReviewUpdateReq {
        private String content;
        private Double rating;
    }
}
