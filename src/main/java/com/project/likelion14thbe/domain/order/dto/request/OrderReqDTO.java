package com.project.likelion14thbe.domain.order.dto.request;

import java.util.List;

public class OrderReqDTO {
    
    public record CreateOrderReqDTO(
            Long memberId,
            List<OrderItemReq> orderItems
    ) {
    }

    public record OrderItemReq(
            Long productId,
            Long quantity
    ) {
    }
}
