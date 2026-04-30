package com.project.likelion14thbe.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class ReviewReqDTO {

    @Getter
    @Setter
    @Schema(description = "리뷰 작성 요청 DTO")
    public static class ReviewCreateReq {

        @Schema(description = "리뷰 내용", example = "이 제품 정말 좋아요 !! 다들 구매하세요 ~!")
        private String content;

        @Schema(description = "평점 (0.0 ~ 5.0)", example = "5.0")
        private Double rating;
    }

    @Getter
    @Setter
    @Schema(description = "리뷰 수정 요청 DTO")
    public static class ReviewUpdateReq {

        @Schema(description = "수정할 리뷰 내용", example = "수정된 리뷰 내용입니다. 정말 좋아요!")
        private String content;

        @Schema(description = "수정할 평점 (0.0 ~ 5.0)", example = "5.0")
        private Double rating;
    }
}