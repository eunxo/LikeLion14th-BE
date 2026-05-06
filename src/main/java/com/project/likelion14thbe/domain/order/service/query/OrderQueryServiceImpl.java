package com.project.likelion14thbe.domain.order.service.query;

import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 조회 전용 트랜잭션
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResDTO.OrderDetailResDto getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));

        return OrderResDTO.OrderDetailResDto.builder()
                .id(order.getMember().getId())
                .orderId(order.getOrderId())
                .orderDate(order.getDate() != null ? order.getDate().toString() : null)
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .build();
    }

    @Override
    public List<OrderResDTO.OrderHistoryRes> getOrderList() {
        return orderRepository.findAll().stream()
                .map(order -> OrderResDTO.OrderHistoryRes.builder()
                        .orderId(order.getOrderId())
                        .orderDate(order.getDate() != null ? order.getDate().toString() : null)
                        .totalAmount(order.getTotalAmount())
                        .status(order.getStatus())
                        .build())
                .collect(Collectors.toList());
    }
}