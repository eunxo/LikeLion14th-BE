package com.project.likelion14thbe.domain.order.converter;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.entity.ProductOrder;
import com.project.likelion14thbe.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

public class OrderConverter {

    public static Order toOrder(Member member) {
        return Order.builder()
                .member(member)
                .status("결제 완료")
                .build();
    }

    public static ProductOrder toProductOrder(Order order, Product product, Integer quantity) {
        return ProductOrder.builder()
                .order(order)
                .product(product)
                .count(quantity)
                .build();

    }

    public static OrderResDTO.OrderCreateRes toCreateResDto(Order order, Integer totalOrderPrice){
        return OrderResDTO.OrderCreateRes.builder()
                .orderId(order.getId())
                .orderStatus(order.getStatus())
                .totalOrderPrice(totalOrderPrice)
                .build();
    }

    public static OrderResDTO.OrderGetListRes.OrderInfo orderInfo(Order order){
        int totalQuantity = order.getProduct().stream()
                .mapToInt(ProductOrder::getCount)
                .sum();

        int totalPrice = order.getProduct().stream()
                .mapToInt(po -> po.getProduct().getProductPrice() * po.getCount())
                .sum();

        return OrderResDTO.OrderGetListRes.OrderInfo.builder()
                .orderId(order.getId())
                .quantity(totalQuantity)
                .totalPrice(totalPrice)
                .status(order.getStatus())
                .orderDate(order.getCreatedAt())
                .build();
    }

    public static OrderResDTO.OrderGetListRes toOrderGetListRes(List<OrderResDTO.OrderGetListRes.OrderInfo> orderInfos){

        return OrderResDTO.OrderGetListRes.builder()
                .datalist(orderInfos)
                .build();
    }
}

