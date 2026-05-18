package com.project.likelion14thbe.domain.order.service.query;

import com.project.likelion14thbe.domain.order.converter.OrderConverter;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResDTO.OrderGetListRes getMyOrderList(String email) {

        List<Order> orderList = orderRepository.findAllByMemberEmail(email);

        List<OrderResDTO.OrderGetListRes.OrderInfo> orderInfos = orderList.stream()
                .map(OrderConverter::orderInfo)
                .toList();

        return OrderConverter.toOrderGetListRes(orderInfos);
    }
}
