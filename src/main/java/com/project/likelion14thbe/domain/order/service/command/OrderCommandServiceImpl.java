package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.order.converter.OrderConverter;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.entity.ProductOrder;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import com.project.likelion14thbe.domain.order.repository.ProductOrderRepository;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderCommandServiceImpl implements OrderCommandService{
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;

    public OrderResDTO.OrderCreateRes createOrder(OrderReqDTO.OrderCreateReq orderCreateReq, Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Order order = OrderConverter.toOrder(member);

        orderRepository.save(order);

        int totalPrice = 0;
        for (OrderReqDTO.OrderCreateReq.OrderItemReq itemReq : orderCreateReq.orderItems()){
            Product product = productRepository.findById(itemReq.productId())
                    .orElseThrow(() -> new IllegalArgumentException("상품 없음" + itemReq.productId()));

            ProductOrder productOrder = OrderConverter.toProductOrder(order, product, itemReq.quantity());
            productOrderRepository.save(productOrder);

            totalPrice += (product.getProductPrice() * itemReq.quantity());
        }

        return OrderConverter.toCreateResDto(order, totalPrice);
    }
}
