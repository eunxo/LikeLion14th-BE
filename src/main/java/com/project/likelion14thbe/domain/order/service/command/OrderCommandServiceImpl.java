package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.exception.MemberErrorCode;
import com.project.likelion14thbe.domain.member.exception.MemberException;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.exception.OrderErrorCode;
import com.project.likelion14thbe.domain.order.exception.OrderException;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.exception.ProductErrorCode;
import com.project.likelion14thbe.domain.product.exception.ProductException;
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
    public void createOrder(final String email, final OrderReqDTO.CreateReq req) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        if (req.getQuantity() == null || req.getQuantity() <= 0) {
            throw new OrderException(OrderErrorCode.ORDER_OUT_OF_STOCK);
        }

        Order order = Order.builder()
                .member(member)
                .product(product)
                .quantity(req.getQuantity())
                .build();

        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(final Long orderId, final String email) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        if (!order.getMember().getEmail().equals(email)) {
            throw new MemberException(MemberErrorCode.MEMBER_FORBIDDEN);
        }

        order.cancel();
    }
}