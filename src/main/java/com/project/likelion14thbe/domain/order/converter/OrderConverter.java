package com.project.likelion14thbe.domain.order.converter;


import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.entity.OrderItem;
import com.project.likelion14thbe.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConverter {

    public static Order toOrder(Member member, Integer totalPrice) {
        return Order.builder()
                .member(member)
                .totalPrice(totalPrice)
                .build();
    }

    public static OrderItem toOrderItem(Order order, Product product, Integer quantity) {
        return OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(quantity)
                .price(product.getPrice())
                .productName(product.getName())
                .build();
    }

    public static OrderResDTO.OrderCreateResult toCreateResult(Order order, OrderItem orderItem) {
        return OrderResDTO.OrderCreateResult.builder()
                .orderId(order.getOrderId())
                .productId(orderItem.getProduct().getProductId())
                .quantity(orderItem.getQuantity())
                .build();
    }

    public static OrderResDTO.OrderSummaryRes toSummary(Order order, OrderItem orderItem) {
        return OrderResDTO.OrderSummaryRes.builder()
                .orderId(order.getOrderId())
                .productName(orderItem.getProductName())
                .quantity(orderItem.getQuantity())
                .status(order.getStatus())
                .build();
    }
}
