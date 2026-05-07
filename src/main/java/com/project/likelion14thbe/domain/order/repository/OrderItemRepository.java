package com.project.likelion14thbe.domain.order.repository;

import com.project.likelion14thbe.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findFirstByProductProductIdAndOrderMemberUserId(Long productId, Long memberId);

    Optional<OrderItem> findFirstByOrderOrderId(Long orderId);
}
