package com.project.likelion14thbe.domain.order.service.command;

import com.project.likelion14thbe.domain.member.entity.Member;
import com.project.likelion14thbe.domain.member.repository.MemberRepository;
import com.project.likelion14thbe.domain.order.converter.OrderConverter;
import com.project.likelion14thbe.domain.order.dto.request.OrderReqDTO;
import com.project.likelion14thbe.domain.order.dto.response.OrderResDTO;
import com.project.likelion14thbe.domain.order.entity.Order;
import com.project.likelion14thbe.domain.order.entity.OrderItem;
import com.project.likelion14thbe.domain.order.repository.OrderRepository;
import com.project.likelion14thbe.domain.product.entity.Product;
import com.project.likelion14thbe.domain.product.repository.ProductRepository;
import com.project.likelion14thbe.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion14thbe.global.apiPayload.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderResDTO.OrderCreateResDto createOrder(OrderReqDTO.CreateOrderReq request, Long memberId) {

        Member member = memberRepository.findByIdAndNotDeleted(memberId)
                .orElseThrow(() -> new CustomException(GeneralErrorCode.MEMBER_NOT_FOUND_404));

        List<OrderItem> orderItems = request.getItems().stream()
                .map(itemReq -> {
                    Product product = productRepository.findById(Long.valueOf(itemReq.getProductId()))
                            .orElseThrow(() -> new CustomException(GeneralErrorCode.NOT_FOUND_404));

                    return OrderConverter.toOrderItem(itemReq, product);
                })
                .collect(Collectors.toList());

        Order order = OrderConverter.toOrder(request, orderItems, member);
        Order savedOrder = orderRepository.save(order);

        return OrderConverter.toOrderCreateResDto(savedOrder);
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(GeneralErrorCode.NOT_FOUND_404));

        order.updateStatus(status);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId, Long memberId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(GeneralErrorCode.NOT_FOUND_404));

        if (!order.getMember().getId().equals(memberId)) {
            throw new CustomException(GeneralErrorCode.FORBIDDEN_403);
        }

        order.cancel();
    }
}