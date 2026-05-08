package com.project.likelion14thbe.domain.order.service.query;

import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.order.converter.OrderConverter;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import com.project.likelion14thbe.domain.order.exception.OrderErrorCode;
import com.project.likelion14thbe.domain.order.exception.OrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Override
    public OrderResDTO.MyOrderListResDTO getMyOrders(Long memberId, Integer page, Integer size) {
        if (memberRepository.findByIdAndNotDeleted(memberId).isEmpty()) {
            throw new OrderException(OrderErrorCode.ORDER_MEMBER_NOT_FOUND);
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderRepository.findAllByMember_IdOrderByCreatedAtDesc(memberId, pageable);
        return OrderConverter.toMyOrderListResDTO(orders);
    }
}
