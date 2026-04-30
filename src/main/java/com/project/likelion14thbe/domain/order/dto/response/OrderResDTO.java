package com.project.likelion14thbe.domain.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class OrderResDTO {

    @Getter
    @Builder
    public static class OrderCreateRes {

        @Schema(description = "요청 성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "ORDER201")
        private String code;

        @Schema(description = "응답 메시지", example = "주문 성공")
        private String message;

        private OrderCreateResult result;
    }

    @Getter
    @Builder
    public static class OrderCreateResult {

        @Schema(description = "주문 ID", example = "1")
        private Long orderId;

        @Schema(description = "상품 ID", example = "1")
        private Long productId;

        @Schema(description = "수량", example = "2")
        private Integer quantity;
    }

    @Getter
    @Builder
    public static class OrderListRes {

        @Schema(description = "요청 성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "ORDER200")
        private String code;

        @Schema(description = "응답 메시지", example = "주문 목록 조회 성공")
        private String message;

        private List<OrderSummaryRes> result;
    }

    @Getter
    @Builder
    public static class OrderSummaryRes {

        @Schema(description = "주문 ID", example = "1")
        private Long orderId;

        @Schema(description = "상품명", example = "무드등")
        private String productName;

        @Schema(description = "수량", example = "2")
        private Integer quantity;

        @Schema(description = "주문 상태", example = "ORDERED")
        private String status;
    }

    @Getter
    @Builder
    public static class OrderCancelRes {

        @Schema(description = "요청 성공 여부", example = "true")
        private boolean isSuccess;

        @Schema(description = "응답 코드", example = "ORDER200")
        private String code;

        @Schema(description = "응답 메시지", example = "주문 취소 성공")
        private String message;
    }
}