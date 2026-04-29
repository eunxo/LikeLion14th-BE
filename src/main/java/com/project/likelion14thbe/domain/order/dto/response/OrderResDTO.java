package com.project.likelion14thbe.domain.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResDTO {

    @Schema(description = "주문 생성 응답 DTO")
    public record CreateOrderResDTO(
            @Schema(description = "주문 ID", example = "100")
            Long orderId,

            @Schema(description = "상품 ID", example = "5")
            Long productId,

            @Schema(description = "수량", example = "2")
            Integer quantity,

            @Schema(description = "총 결제 금액", example = "6000")
            Integer totalPrice,

            @Schema(description = "주문 상태", example = "PENDING",
                    allowableValues = {"PENDING", "PAID", "DELIVERED", "CANCELLED"})
            String status,

            @Schema(description = "주문 일시", example = "2026-04-29T12:00:00")
            LocalDateTime createdAt
    ) {
    }

    @Schema(description = "내 주문 목록 — 개별 주문 아이템")
    public record MyOrderItemDTO(
            @Schema(description = "주문 ID", example = "100")
            Long orderId,

            @Schema(description = "상품 ID", example = "5")
            Long productId,

            @Schema(description = "상품명", example = "사과")
            String productName,

            @Schema(description = "수량", example = "2")
            Integer quantity,

            @Schema(description = "총 결제 금액", example = "6000")
            Integer totalPrice,

            @Schema(description = "주문 상태", example = "DELIVERED")
            String status,

            @Schema(description = "주문 일시", example = "2026-04-29T12:00:00")
            LocalDateTime createdAt
    ) {
    }

    @Schema(description = "내 주문 목록 응답 DTO (페이징 포함)")
    public record MyOrderListResDTO(
            @Schema(description = "전체 주문 개수", example = "12")
            Long totalElements,

            @Schema(description = "전체 페이지 수", example = "2")
            Integer totalPages,

            @Schema(description = "현재 페이지 번호 (0부터 시작)", example = "0")
            Integer currentPage,

            @Schema(description = "페이지당 개수", example = "10")
            Integer size,

            @Schema(description = "마지막 페이지 여부", example = "false")
            Boolean isLast,

            @Schema(description = "내 주문 목록")
            List<MyOrderItemDTO> orderList
    ) {
    }
}
