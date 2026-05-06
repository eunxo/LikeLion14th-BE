package com.project.likelion14thbe.domain.review.dto.request;

import lombok.*;

public class ReviewReqDTO {
    @Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class ReviewCreateReq {
        private String content;
        private Double rating;
    }

    @Getter
    @Setter
    public static class ReviewUpdateReq {
        private String content;
        private Double rating;
    }
}
