package com.project.likelion14thbe.domain.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ReviewResDTO {

    @Getter
    @Builder
    public static class ReviewCreateRes {

        @Schema(description = "요청 성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "REVIEW201")
        private String code;

        @Schema(description = "응답 메시지", example = "리뷰 생성 성공")
        private String message;

        @Schema(description = "리뷰 생성 결과")
        private ReviewCreateResult result;
    }

    @Getter
    @Builder
    public static class ReviewCreateResult {

        @Schema(description = "리뷰 ID", example = "1")
        private Long reviewId;

        @Schema(description = "상품 ID", example = "1")
        private Long productId;

        @Schema(description = "리뷰 내용", example = "상품이 정말 좋아요.")
        private String content;

        @Schema(description = "별점", example = "5")
        private Integer rating;
    }

    @Getter
    @Builder
    public static class ReviewListRes {

        @Schema(description = "요청 성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "REVIEW200")
        private String code;

        @Schema(description = "응답 메시지", example = "리뷰 목록 조회 성공")
        private String message;

        @Schema(description = "리뷰 목록")
        private List<ReviewSummaryRes> result;
    }

    @Getter
    @Builder
    public static class ReviewSummaryRes {

        @Schema(description = "리뷰 ID", example = "1")
        private Long reviewId;

        @Schema(description = "상품 ID", example = "1")
        private Long productId;

        @Schema(description = "작성자 이름", example = "홍길동")
        private String writerName;

        @Schema(description = "리뷰 내용", example = "상품이 정말 좋아요.")
        private String content;

        @Schema(description = "별점", example = "5")
        private Integer rating;
    }

    @Getter
    @Builder
    public static class ReviewDetailRes {

        @Schema(description = "요청 성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "REVIEW200")
        private String code;

        @Schema(description = "응답 메시지", example = "리뷰 단일 조회 성공")
        private String message;

        @Schema(description = "리뷰 상세 데이터")
        private ReviewDetailResult result;
    }

    @Getter
    @Builder
    public static class ReviewDetailResult {

        @Schema(description = "리뷰 ID", example = "1")
        private Long reviewId;

        @Schema(description = "상품 ID", example = "1")
        private Long productId;

        @Schema(description = "작성자 이름", example = "홍길동")
        private String writerName;

        @Schema(description = "리뷰 내용", example = "상품이 정말 좋아요.")
        private String content;

        @Schema(description = "별점", example = "5")
        private Integer rating;

        @Schema(description = "댓글 목록")
        private List<CommentRes> comments;
    }

    @Getter
    @Builder
    public static class ReviewUpdateRes {

        @Schema(description = "요청 성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "REVIEW200")
        private String code;

        @Schema(description = "응답 메시지", example = "리뷰 수정 성공")
        private String message;

        @Schema(description = "리뷰 수정 결과")
        private ReviewUpdateResult result;
    }

    @Getter
    @Builder
    public static class ReviewUpdateResult {

        @Schema(description = "리뷰 ID", example = "1")
        private Long reviewId;

        @Schema(description = "수정된 리뷰 내용", example = "사용해보니 더 만족스럽습니다.")
        private String content;

        @Schema(description = "수정된 별점", example = "4")
        private Integer rating;
    }

    @Getter
    @Builder
    public static class ReviewDeleteRes {

        @Schema(description = "요청 성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "REVIEW200")
        private String code;

        @Schema(description = "응답 메시지", example = "리뷰 삭제 성공")
        private String message;
    }

    @Getter
    @Builder
    public static class CommentCreateRes {

        @Schema(description = "요청 성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "COMMENT201")
        private String code;

        @Schema(description = "응답 메시지", example = "댓글 등록 성공")
        private String message;

        @Schema(description = "댓글 생성 결과")
        private CommentRes result;
    }

    @Getter
    @Builder
    public static class CommentRes {

        @Schema(description = "댓글 ID", example = "1")
        private Long commentId;

        @Schema(description = "작성자 이름", example = "홍길동")
        private String writerName;

        @Schema(description = "댓글 내용", example = "좋은 리뷰 감사합니다.")
        private String content;
    }

    @Getter
    @Builder
    public static class CommentDeleteRes {

        @Schema(description = "요청 성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "COMMENT200")
        private String code;

        @Schema(description = "응답 메시지", example = "댓글 삭제 성공")
        private String message;
    }
}