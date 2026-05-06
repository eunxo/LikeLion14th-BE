package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResDTO.OrderCreateResDto createOrder(OrderReqDTO.CreateOrderReq request) {
        Order order = Order.builder()
                .build();

        Order savedOrder = orderRepository.save(order);

        // 생성된 주문의 정보 반환
        return OrderResDTO.OrderCreateResDto.builder()
                .id(savedOrder.getMember().getId())
                .orderId(savedOrder.getOrderId())
                .build();
    }
}