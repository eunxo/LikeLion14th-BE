package com.project.likelion14thbe.domain.order.service.query;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.exception.MemberErrorCode;
import com.project.likelion14thbe.domain.member.exception.MemberException;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.order.converter.OrderConverter;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.exception.OrderErrorCode;
import com.project.likelion14thbe.domain.order.exception.OrderException;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Override
    public OrderResDTO.OrderDeatilRes getOrder(CustomUserDetails customUserDetails, Long orderId) {
        Member member = memberRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        if (member.getId().equals(order.getMember().getId())) {
            return OrderConverter.toOrderResDTO(order);
        }
        else throw new OrderException(OrderErrorCode.ORDER_UNAUTHORIZED);
    }

    @Override
    public List<OrderResDTO.OrderDeatilRes> getOrderList(CustomUserDetails customUserDetails) {

        Long memberId = memberRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)).getId();

        List<Order> orderList = orderRepository.findAllByMemberId(memberId);

        return orderList.stream().map(OrderConverter::toOrderResDTO).toList();
    }
}
