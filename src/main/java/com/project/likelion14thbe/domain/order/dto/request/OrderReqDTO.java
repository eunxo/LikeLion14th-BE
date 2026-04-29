package com.project.likelion14thbe.domain.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class OrderReqDTO {

    @Getter
    public static class OrderCreateReq {

        @Schema(description = "상품 ID", example = "1")
        private Long productId;

        @Schema(description = "수량", example = "2")
        private Integer quantity;
    }
}