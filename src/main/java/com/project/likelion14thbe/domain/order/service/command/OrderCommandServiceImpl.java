package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.exception.MemberErrorCode;
import com.project.likelion14thbe.domain.member.exception.MemberException;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.order.converter.OrderConverter;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.entity.OrderItem;
import com.project.likelion14thbe.domain.order.exception.OrderErrorCode;
import com.project.likelion14thbe.domain.order.exception.OrderException;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.exception.ProductErrorCode;
import com.project.likelion14thbe.domain.product.exception.ProductException;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import com.project.likelion14thbe.global.security.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderCommandServiceImpl implements OrderCommandService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public String createOrder(CustomUserDetails customUserDetails, OrderReqDTO.CreateOrderReqDTO createOrderReqDTO) {

        // 1. 회원 조회
        Member member = memberRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 2. 재고 확인 및 총 가격, 총 수량 사전 계산
        double totalPrice = 0.0;
        long totalQuantity = 0L;

        for (OrderReqDTO.OrderItemReq orderItemReq : createOrderReqDTO.orderItems()) {
            Product product = productRepository.findById(orderItemReq.productId())
                    .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

            if (product.getQuantity() < orderItemReq.quantity()) {
                throw new IllegalArgumentException("상품의 재고가 부족합니다.");
            }
            totalPrice += (product.getPrice() * orderItemReq.quantity());
            totalQuantity += orderItemReq.quantity();
        }

        // 3. 주문(Order) 생성 (DB 저장 전)
        Order order = OrderConverter.toOrder(member, totalPrice, totalQuantity);

        // 4. 상품의 재고 차감 및 OrderItem 생성 후 Order에 연결
        for (OrderReqDTO.OrderItemReq orderItemReq : createOrderReqDTO.orderItems()) {
            Product product = productRepository.findById(orderItemReq.productId())
                    .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

            // 엔티티에 만들어둔 메서드로 재고 차감
            product.decreaseQuantity(orderItemReq.quantity());

            OrderItem orderItem = OrderConverter.toOrderItem(order, product, orderItemReq.quantity());

            // Order 엔티티의 리스트에 추가
            order.getOrderItems().add(orderItem);
        }

        // 5. DB 저장
        orderRepository.save(order);

        return "주문 생성 성공";
    }

    @Override
    public void deleteOrder(Long orderId){
        // 주문 조회
        Order order = orderRepository.findByIdAndNotDeleted(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        // 재고 복구
        for (OrderItem orderItem : order.getOrderItems()) {
            // 해당 제품 조회
            Product product = orderItem.getProduct();

            // 제품 재고 복구
            product.increaseQuantity(orderItem.getQuantity());
        }

        // Order soft delete 처리
        order.deleteOrder();
    }
}
