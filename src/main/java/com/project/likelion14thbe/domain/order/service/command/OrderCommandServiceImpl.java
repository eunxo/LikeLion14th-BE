package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.order.converter.OrderConverter;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
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
    public void createOrder(Long memberId, OrderReqDTO.CreateReq req) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));


        Order order = OrderConverter.toOrder(req, member, product);

        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId, Long memberId) {
        // 1. 취소되지 않은 주문 조회
        Order order = orderRepository.findByIdAndNotDeleted(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        // 2. 권한 확인 (본인 주문인지)
        if (!order.getMember().getId().equals(memberId)) {
            throw new OrderException(OrderErrorCode.ORDER_UNAUTHORIZED);
        }

        // 3. 주문 취소 (Soft Delete 실행)
        order.cancel();
    }
}
