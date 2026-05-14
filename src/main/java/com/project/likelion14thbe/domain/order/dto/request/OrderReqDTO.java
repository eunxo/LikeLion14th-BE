package com.project.likelion14thbe.domain.order.dto.request;

import java.util.List;

public class OrderReqDTO {

    public record OrderCreateReq(
            List<OrderItemReq> orderItems
    ) {
        public record OrderItemReq(
                Long productId,
                Integer quantity
        ) {
        }
    }

    public record ChangeStatusDTO(
            String status
    ){

    }
}
