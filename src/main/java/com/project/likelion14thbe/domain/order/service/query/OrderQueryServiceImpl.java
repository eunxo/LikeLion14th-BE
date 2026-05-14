package com.project.likelion14thbe.domain.order.service.query;

import com.project.likelion14thbe.domain.order.converter.OrderConverter;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.order.repository.OrderItemRepository;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<OrderResDTO.OrderSummaryRes> getOrders() {
        Long memberId = memberRepository.findFirstByDeletedAtIsNullOrderByUserIdAsc()
                .map(member -> member.getUserId())
                .orElse(null);
        if (memberId == null) {
            return List.of();
        }

        return orderRepository.findAllByMemberUserIdOrderByCreatedAtDesc(memberId).stream()
                .map(order -> orderItemRepository.findFirstByOrderOrderId(order.getOrderId())
                        .map(item -> OrderConverter.toSummary(order, item))
                        .orElse(null))
                .filter(java.util.Objects::nonNull)
                .toList();
    }
}
