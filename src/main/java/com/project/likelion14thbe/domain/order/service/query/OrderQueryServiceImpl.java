package com.project.likelion14thbe.domain.order.service.query;

import com.project.likelion14thbe.domain.order.converter.OrderConverter;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import com.project.likelion14thbe.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion14thbe.global.apiPayload.exception.CustomException;
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
                .orElseThrow(() -> new CustomException(GeneralErrorCode.NOT_FOUND_404));

        return OrderConverter.toOrderDetailResDto(order);
    }

    @Override
    public List<OrderResDTO.OrderHistoryRes> getOrderList() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(OrderConverter::toOrderHistoryRes)
                .collect(Collectors.toList());
    }
    @Override
    public List<OrderResDTO.OrderHistoryRes> getMyOrders(Long memberId) {
        List<Order> orders = orderRepository.findByMemberId(memberId);  // 성능 개선

        return orders.stream()
                .map(OrderConverter::toOrderHistoryRes)
                .collect(Collectors.toList());
    }
}