package com.project.likelion14thbe.domain.order.converter;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.entity.OrderItem;
import com.project.likelion14thbe.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

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

    public static Order toOrder (Member member, double totalPrice, long totalQuantity) {
        return Order.builder()
                .member(member)
                .orderNumber(System.currentTimeMillis()) // 임시로 시간값 사용
                .status("주문 완료")
                .totalPrice(totalPrice)
                .totalQuantity(totalQuantity)
                .orderItems(new ArrayList<>())
                .build();
    }

    public static OrderItem toOrderItem(Order order, Product product, long itemQuantity) {
        return OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(itemQuantity)
                .orderPrice(product.getPrice())
                .build();
    }
}