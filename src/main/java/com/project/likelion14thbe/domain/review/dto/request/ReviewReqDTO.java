package com.project.likelion14thbe.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewReqDTO {

    @Schema(description = "리뷰 생성 요청 DTO")
    public record CreateReviewReqDTO(
            @Schema(description = "별점 (0.5 이상 5.0 이하)", example = "4.5")
            @NotNull(message = "별점은 필수입니다.")
            @DecimalMin(value = "0.5", message = "별점은 0.5 이상이어야 합니다.")
            @DecimalMax(value = "5.0", message = "별점은 5.0 이하여야 합니다.")
            Double rating,

            @Schema(description = "리뷰 내용", example = "이 제품 정말 좋아요")
            @NotBlank(message = "리뷰 내용은 필수입니다.")
            String content
    ) {
    }

    @Schema(description = "리뷰 수정 요청 DTO (부분 수정 — 필드 선택)")
    public record UpdateReviewReqDTO(
            @Schema(description = "별점 (0.5 이상 5.0 이하, 선택)", example = "5.0")
            @DecimalMin(value = "0.5", message = "별점은 0.5 이상이어야 합니다.")
            @DecimalMax(value = "5.0", message = "별점은 5.0 이하여야 합니다.")
            Double rating,

            @Schema(description = "리뷰 내용 (선택)", example = "다시 먹어봤는데 더 맛있네요!")
            String content
    ) {
    }
}
