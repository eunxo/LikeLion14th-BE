package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.exception.MemberErrorCode;
import com.project.likelion14thbe.domain.member.exception.MemberException;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.order.converter.OrderConverter;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.entity.OrderItem;
import com.project.likelion14thbe.domain.order.exception.OrderErrorCode;
import com.project.likelion14thbe.domain.order.exception.OrderException;
import com.project.likelion14thbe.domain.order.repository.OrderItemRepository;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.exception.ProductErrorCode;
import com.project.likelion14thbe.domain.product.exception.ProductException;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Override
    public OrderResDTO.OrderCreateResult createOrder(String email, OrderReqDTO.OrderCreateReq request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));
        Member member = findActiveMemberByEmail(email);

        int quantity = request.getQuantity() == null ? 1 : request.getQuantity();
        Order order = OrderConverter.toOrder(member, product.getPrice() * quantity);
        orderRepository.save(order);

        OrderItem orderItem = OrderConverter.toOrderItem(order, product, quantity);
        orderItemRepository.save(orderItem);
        return OrderConverter.toCreateResult(order, orderItem);
    }

    @Override
    public void cancelOrder(String email, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        if (!order.getMember().getEmail().equals(email)) {
            throw new OrderException(OrderErrorCode.ORDER_FORBIDDEN);
        }

        order.cancel();
    }

    private Member findActiveMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .filter(member -> member.getDeletedAt() == null)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
