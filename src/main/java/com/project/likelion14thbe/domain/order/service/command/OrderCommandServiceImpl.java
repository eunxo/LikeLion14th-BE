package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.order.converter.OrderConverter;
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
        Order order = OrderConverter.toOrder(request);
        Order savedOrder = orderRepository.save(order);
        return OrderConverter.toOrderCreateResDto(savedOrder);

    }
}