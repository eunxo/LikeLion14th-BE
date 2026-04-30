package com.project.likelion14thbe.domain.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class OrderResDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "주문 내역 응답 DTO")
    public static class OrderHistoryRes {

        @Schema(description = "주문 ID", example = "20260430-001")
        private String orderId;

        @Schema(description = "주문 날짜", example = "2026-04-30T13:37:10")
        private String orderDate;

        @Schema(description = "총 주문 금액", example = "75000")
        private Integer totalAmount;

        @Schema(description = "주문 상태", example = "COMPLETED")
        private String status;

        @Schema(description = "주문 상품 상세 목록")
        private List<OrderItemRes> orderItems;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemRes {
        @Schema(description = "상품명", example = "Minimal Stand")
        private String productName;

        @Schema(description = "주문 수량", example = "2")
        private Integer quantity;

        @Schema(description = "상품 가격", example = "25000")
        private Integer price;
    }
}