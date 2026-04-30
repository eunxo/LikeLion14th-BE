package com.project.likelion14thbe.domain.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderReqDTO {

    @Schema(description = "주문 생성 요청 DTO")
    public record CreateOrderReqDTO(
            @Schema(description = "주문할 상품 아이디", example = "5")
            @NotNull(message = "상품 아이디는 필수입니다.")
            Long productId,
            @Schema(description = "수량 (1 이상)", example = "2")
            @NotNull(message = "수량은 필수입니다.")
            @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
            Integer quantity,
            @Schema(description = "배송 주소", example = "서울특별시 종로구 ...")
            @NotBlank(message = "배송 주소는 필수입니다.")
            String address
    ) {
    }
}
