package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.exception.MemberErrorCode;
import com.project.likelion14thbe.domain.member.exception.MemberException;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.order.converter.OrderConverter;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.entity.ProductOrder;
import com.project.likelion14thbe.domain.order.exception.OrderErrorCode;
import com.project.likelion14thbe.domain.order.exception.OrderException;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import com.project.likelion14thbe.domain.order.repository.ProductOrderRepository;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.exception.ProductErrorCode;
import com.project.likelion14thbe.domain.product.exception.ProductException;
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

    public OrderResDTO.OrderCreateRes createOrder(OrderReqDTO.OrderCreateReq orderCreateReq, String email){
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Order order = OrderConverter.toOrder(member);

        orderRepository.save(order);

        int totalPrice = 0;
        for (OrderReqDTO.OrderCreateReq.OrderItemReq itemReq : orderCreateReq.orderItems()){
            Product product = productRepository.findByAndNotDeleted(itemReq.productId())
                    .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

            ProductOrder productOrder = OrderConverter.toProductOrder(order, product, itemReq.quantity());
            productOrderRepository.save(productOrder);

            totalPrice += (product.getProductPrice() * itemReq.quantity());
        }

        return OrderConverter.toCreateResDto(order, totalPrice);
    }

    @Override
    public void changeStatus(Long orderId, String email, OrderReqDTO.ChangeStatusDTO dto){
        // 주문정보 조회
        Order order = orderRepository.findByIdAndNotDeleted(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        if (!order.getMember().getEmail().equals(email)){
            throw new OrderException(OrderErrorCode.ORDER_FORBIDDEN);
        }

        order.changeStatus(dto.status());
    }

    @Override
    public void deleteOrder(Long orderId, String email){
        // 주문 정보 조회
        Order order = orderRepository.findByIdAndNotDeleted(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        // 주문 정보 접근 권한 확인
        if (!order.getMember().getEmail().equals(email)) {
            throw new OrderException(OrderErrorCode.ORDER_FORBIDDEN);
        }

        order.delete();
    }
}
