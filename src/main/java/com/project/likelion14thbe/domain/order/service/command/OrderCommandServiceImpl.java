package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.order.converter.OrderConverter;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.entity.OrderItem;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResDTO.OrderCreateResDto createOrder(OrderReqDTO.CreateOrderReq request) {
//        1. 주문아이템 목록 만들기
//                리퀘스트 안에 있는 재료들로 오더 아이템 엔티티를 만드는 컨버터 + 서비스 메서드 만들기 - 리스트형태로
//
//
//        2. 만든 주문 아이템 목록 변수를 toOrder메서드의 매개변수로 넣어주기

        // 1. 주문아이템 목록 만들기
        // request.getItems() (List<OrderItemReq>)를 List<OrderItem>으로 변환합니다.
        List<OrderItem> orderItems = request.getItems().stream()
                .map(itemReq -> OrderConverter.toOrderItem(itemReq)) // 각 요소를 엔티티로 변환
                .collect(Collectors.toList());

        // 2. 만든 주문 아이템 목록 변수를 toOrder 메서드의 매개변수로 넣어주기
        Order order = OrderConverter.toOrder(request, orderItems);

        Order savedOrder = orderRepository.save(order);

        return OrderConverter.toOrderCreateResDto(savedOrder);

//        Order order = OrderConverter.toOrder(request, 주문 아이템 목록);
//        Order savedOrder = orderRepository.save(order);
//        return OrderConverter.toOrderCreateResDto(savedOrder);

    }
}