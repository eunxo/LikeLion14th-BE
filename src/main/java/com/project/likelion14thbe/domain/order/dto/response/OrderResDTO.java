package com.project.likelion14thbe.domain.order.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public class OrderResDTO {

    @Builder
    public record OrderDeatilRes(
            Long orderId,
            LocalDateTime orderDate,
            Long totalQuantity,
            Double totalAmount,
            String status
    ) {
    }
}
