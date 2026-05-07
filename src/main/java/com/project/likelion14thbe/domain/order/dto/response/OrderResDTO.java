package com.project.likelion14thbe.domain.order.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

public class OrderResDTO {

    @Builder
    public record OrderGetListRes(
            List<OrderInfo> datalist
    ) {

        @Builder
        public record OrderInfo(
                Long orderId,
                Integer quantity,
                Integer totalPrice,
                String status,
                LocalDateTime orderDate
        ) {
        }
    }

    @Builder
    public record OrderCreateRes(
            Long orderId,
            String orderStatus,
            Integer totalOrderPrice,
            LocalDateTime orderDate
    ) {
    }

    @Builder
    public record OrderCancelRes(
            Long orderId,
            String orderStatus,
            LocalDateTime cancelDate
    ) {
    }
}
