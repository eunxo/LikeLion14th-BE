package com.project.likelion14thbe.domain.order.service.query;

import com.project.likelion14thbe.domain.order.converter.OrderConverter;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResDTO.OrderDeatilRes getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        return OrderConverter.toOrderResDTO(order);
    }

    @Override
    public List<OrderResDTO.OrderDeatilRes> getOrderList(Long memberId) {

        List<Order> orderList = orderRepository.findAllByMemberId(memberId);

        // 이 부분은 잘 모르겠어서 AI 참고했습니다..
        return orderList.stream().map(OrderConverter::toOrderResDTO).toList();
    }
}
