package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.order.converter.OrderConverter;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
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
    public void createOrder(Long userId, OrderReqDTO.CreateReq req) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));


        Order order = OrderConverter.toOrder(req, member, product);

        orderRepository.save(order);
    }
}
