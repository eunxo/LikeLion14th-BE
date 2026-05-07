package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.order.converter.OrderConverter;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.exception.OrderErrorCode;
import com.project.likelion14thbe.domain.order.exception.OrderException;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderResDTO.CreateOrderResDTO createOrder(Long memberId, OrderReqDTO.CreateOrderReqDTO request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_MEMBER_NOT_FOUND));
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_PRODUCT_NOT_FOUND));

        Integer totalPrice = product.getPrice() * request.quantity();
        Order order = OrderConverter.toOrder(member, product, request, totalPrice);
        Order saved = orderRepository.save(order);
        return OrderConverter.toCreateOrderResDTO(saved);
    }

    @Override
    public void cancelOrder(Long orderId, Long memberId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        // 본인 주문 검증 (시큐리티 미적용으로 임시)
        if (!order.getMember().getId().equals(memberId)) {
            throw new OrderException(OrderErrorCode.ORDER_FORBIDDEN);
        }

        orderRepository.delete(order);  // hard delete
    }
}
