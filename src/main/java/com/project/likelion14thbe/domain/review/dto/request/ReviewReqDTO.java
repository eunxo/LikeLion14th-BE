package com.project.likelion14thbe.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class ReviewReqDTO {

    @Getter
    public static class ReviewCreateReq {

        @Schema(description = "리뷰 내용", example = "상품이 정말 좋아요.")
        private String content;

        @Schema(description = "별점", example = "5")
        private Integer rating;

        @Schema(description = "주문상세 ID", example = "1")
        private Long orderItemId;
    }

    @Getter
    public static class ReviewUpdateReq {

        @Schema(description = "수정할 리뷰 내용", example = "사용해보니 더 만족스럽습니다.")
        private String content;

        @Schema(description = "수정할 별점", example = "4")
        private Integer rating;
    }

    @Getter
    public static class CommentCreateReq {

        @Schema(description = "댓글 내용", example = "좋은 리뷰 감사합니다.")
        private String content;
    }
}