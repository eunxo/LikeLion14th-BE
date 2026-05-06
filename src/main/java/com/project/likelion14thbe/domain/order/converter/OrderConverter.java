package com.project.likelion14thbe.domain.order.converter;

import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConverter {

    public static OrderResDTO.OrderDeatilRes toOrderResDTO(Order order) {
        return OrderResDTO.OrderDeatilRes.builder()
                .orderId(order.getId())
                .orderDate(order.getCreatedAt())
                .totalQuantity(order.getTotalQuantity())
                .totalAmount(order.getTotalPrice())
                .status(order.getStatus())
                .build();
    }
}